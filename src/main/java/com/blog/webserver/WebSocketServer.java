package com.blog.webserver;

import com.alibaba.fastjson.JSON;
import com.blog.pojo.Message;
import com.blog.pojo.MsgStore;
import com.blog.service.MsgStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/chat/webSocket/{uid}")
@Component
public class WebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

	 //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineNum = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    /**
     * 解决websocket无法注入bean
     */
    private static MsgStoreService msgStoreService;

    @Autowired
    public void setMsgStoreService(MsgStoreService msgStoreService){
        WebSocketServer.msgStoreService = msgStoreService;
    }

    //发送消息
    public void sendMessage(Session session, String message) throws IOException {
        if(session != null){
            synchronized (session) {
                session.getBasicRemote().sendText(message);
            }
        }
    }
    //给指定用户uid发送信息
    public void sendInfo(String uid, String message){
        Session session = sessionPools.get(uid);
        try {
            sendMessage(session, message);
        }catch (Exception e){
            logger.info(e.getMessage());
        }
    }

    // 群发消息
    public void broadcast(String message){
    	for (Session session: sessionPools.values()) {
            try {
                sendMessage(session, message);
            } catch(Exception e){
                logger.info(e.getMessage());
                continue;
            }
        }
    }

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "uid") String uid){
        sessionPools.put(uid, session);
        addOnlineCount();
        System.out.println(uid + "加入webSocket！当前人数为" + onlineNum);
        //上线后查看消息库是否有信息,有则按时间排序获取
    }

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam(value = "uid") String uid){
        sessionPools.remove(uid);
        subOnlineCount();
        System.out.println(uid + "断开webSocket连接！当前人数为" + onlineNum);
    }

    //收到客户端信息后，根据接收人的username把消息推下去或者群发
    @OnMessage
    public void onMessage(String message) throws IOException{
        System.out.println("server get" + message);
        Message msg=JSON.parseObject(message, Message.class);
		msg.setDate(new Date());
		//收到消息判断接收方是否在线，不在则存入消息库  在则存入消息库并发送
        Session touser = sessionPools.get(msg.to);
        if (touser==null){
            System.out.println("对方不在线");
            msgStoreService.saveMessageUnRead(msg);
        }else if(msg.getType().equals("heartbeat")){
            sendInfo(msg.getTo(), JSON.toJSONString(msg,true));
        }else {
            Integer msgid = msgStoreService.saveMessageUnRead(msg);
            msg.setMsgid(msgid);
            sendInfo(msg.getTo(), JSON.toJSONString(msg,true));
        }
    }


    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("发生错误");
        logger.info(throwable.getMessage());
    }

    public static void isMsg(String uid){
        List<MsgStore> msgList = msgStoreService.isHasUnReadMsg(uid);
        if (msgList.size()==0){
            System.out.println("没有新消息");
        }else {
            System.out.println(msgList);
        }
    }

    //发送信息数提示
    public void sendtips(String uid){
        Session session = sessionPools.get(uid);
        int msgCount = msgStoreService.getUnReadMsgCount(uid);
        Message message = new Message();
        message.setFrom(uid);
        message.setTo(uid);
        message.setText(String.valueOf(msgCount));
        message.setType("unread");
        try {
            sendMessage(session, JSON.toJSONString(message));
        }catch (Exception e){
            logger.info(e.getMessage());
        }
    }

    public static void addOnlineCount(){
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }
    
    public static AtomicInteger getOnlineNumber() {
        return onlineNum;
    }
    
    public static ConcurrentHashMap<String, Session> getSessionPools() {
        return sessionPools;
    }
}
