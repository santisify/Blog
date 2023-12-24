package com.blog.controller;

import com.blog.pojo.*;
import com.blog.service.ChatTabService;
import com.blog.service.MsgStoreService;
import com.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatTabService chatTabService;

    @Autowired
    private UserService userService;

    @Autowired
    private MsgStoreService msgStoreService;

    @Autowired
    private HttpSession session;

    @GetMapping("/chat")
    public String chartRoom() {
        try {
            return "user/chat";
        } catch (Exception e) {
            logger.error("跳转到聊天界面出现异常：" + e);
            return "error/500";
        }
    }

    @GetMapping("/chat/{uid}")
    public String chartRoomWithUser(@PathVariable(value = "uid", required = true) String uid, Model model) {
        try {
            User loginUser = (User) session.getAttribute("loginUser");
            User u = userService.getUserByUid(uid);
            if (loginUser != null && u != null) {
                chatTabService.addChatObj(uid, loginUser.getUid());
                model.addAttribute("receiveruid", uid);
            }
            return "user/chat";
        } catch (Exception e) {
            logger.error("跳转到聊天界面出现异常：" + e);
            return "error/500";
        }
    }

    /**
     * 根据用户id获取该用户聊天对象
     */
    @PostMapping("/chat/onlineusers")
    public String getAllChatObj(@RequestParam("currentuser") String uid, Model model) {
        try {
            List<ChatTab> objTab = chatTabService.getObjTab(uid);
            HashSet<Obj> set = new HashSet<>();
            for (ChatTab chatTab : objTab) {
                if (uid.equals(chatTab.getUid())) {
                    User user = userService.getUserByUid(chatTab.getOuid());
                    Obj obj = new Obj();
                    obj.setUid(user.getUid());
                    obj.setName(user.getNickname());
                    obj.setAvatar(user.getAvatar());
                    //获取最近最新一条数据显示
                    MsgStore lastMsg = msgStoreService.getLastMsg(obj.getUid(), uid);
                    int count = msgStoreService.getUnReadMsgCountWithOther(uid, obj.getUid());
                    if (lastMsg == null) {
                        obj.setLastmsg(" ");
                        obj.setMsgcount(count);
                    } else {
                        obj.setLastmsg(lastMsg.getMsgContent());
                        obj.setTime(lastMsg.getMsgTime());
                        obj.setMsgcount(count);
                    }
                    set.add(obj);
                } else if (uid.equals(chatTab.getOuid())) {

                    User user = userService.getUserByUid(chatTab.getUid());
                    Obj obj = new Obj();
                    obj.setUid(user.getUid());
                    obj.setName(user.getNickname());
                    obj.setAvatar(user.getAvatar());
                    //获取最近最新一条数据显示
                    MsgStore lastMsg = msgStoreService.getLastMsg(obj.getUid(), uid);
                    int count = msgStoreService.getUnReadMsgCountWithOther(uid, obj.getUid());
                    if (lastMsg == null) {
                        obj.setLastmsg(" ");
                        obj.setMsgcount(count);
                    } else {
                        obj.setLastmsg(lastMsg.getMsgContent());
                        obj.setTime(lastMsg.getMsgTime());
                        obj.setMsgcount(count);
                    }
                    set.add(obj);
                }
            }
            System.out.println(set);
            model.addAttribute("ulist", set);
            return "user/chat::ulist";
        } catch (Exception e) {
            logger.error("聊天对象获取失败" + e);
            return "user/chat::ulist";
        }
    }

    @PostMapping("/chat/msgstore")
    @ResponseBody
    public List<MsgStore> getAllMessage(@RequestParam("ruid") String receiveruid, @RequestParam("suid") String senderuid, @RequestParam("page") int page) {
        try {
            List<MsgStore> msgStoreList = msgStoreService.getUnReadMsg(receiveruid, senderuid);
            //打开聊天窗口 如果有未读消息，先返回未读消息
            if (msgStoreList.size() > 0) {
                int msgCount = msgStoreService.getUnReadMsgCount(senderuid);
                session.setAttribute("msgCount", msgCount);
                return msgStoreList;
            }
            //打开聊天窗口 没有未读消息就返回最近前十条信息
            PageResult<MsgStore> pageResult = msgStoreService.getNearMsg(receiveruid, senderuid, page, 15);
            if (pageResult.getDatas().size() == 0) {
                return pageResult.getDatas();
            }
            int msgCount = msgStoreService.getUnReadMsgCount(senderuid);
            session.setAttribute("msgCount", msgCount);
            msgStoreList = pageResult.getDatas();
            Collections.reverse(msgStoreList);
            return msgStoreList;

        } catch (Exception e) {
            logger.error("消息仓库加载失败" + e);
            return new ArrayList<MsgStore>();
        }
    }

    /**
     * 修改消息为已读
     */
    @ResponseBody
    @PostMapping("/chat/changemsgstatus")
    public void changeMsgToRead(@RequestParam(name = "id", required = true) Integer id) {
        msgStoreService.changeMsgStatus(id);
    }

    /**
     * 刷新信息数
     */
    @ResponseBody
    @PostMapping("/chat/flushmsgcount")
    public void flushMsgCount() {
        User user = (User) session.getAttribute("loginUser");
        int msgCount = msgStoreService.getUnReadMsgCount(user.getUid());
        session.setAttribute("msgCount", msgCount);
        return;
    }
}
