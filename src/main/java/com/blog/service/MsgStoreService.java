package com.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.blog.mapper.MsgStoreMapper;
import com.blog.pojo.Message;
import com.blog.pojo.MsgStore;
import com.blog.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class MsgStoreService {


    @Autowired
    private MsgStoreMapper msgStoreMapper;

    //保存离线消息,返回新消息id
    public Integer saveMessageUnRead(Message msg) {
        MsgStore msgStore = new MsgStore();
        msgStore.setReceiverUid(msg.getTo());
        msgStore.setSenderUid(msg.getFrom());
        msgStore.setMsgTime(msg.getDate());
        msgStore.setMsgContent(msg.getText());
        msgStore.setMsgType(0);
        msgStore.setMsgIsread(0);
        msgStoreMapper.insertSelective(msgStore);
        return msgStore.getId();
    }

    //修改消息状态为已读
    public void changeMsgStatus(int id) {
        msgStoreMapper.updateMsgReadStatus(Collections.singletonList(id));
    }

    //登录用户时候有未读消息/新消息
    public List<MsgStore> isHasUnReadMsg(String uid) {
        Example example = new Example(MsgStore.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("receiverUid", uid).andEqualTo("msgIsread", 0);
        example.setOrderByClause("msg_time" + " " + "desc");
        return msgStoreMapper.selectByExample(example);
    }

    //获取用户未读消息数
    public int getUnReadMsgCount(String uid) {
        Example example = new Example(MsgStore.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("receiverUid", uid).andEqualTo("msgIsread", 0);
        return msgStoreMapper.selectCountByExample(example);
    }

    /**
     * 获取与各个用户的聊天未读消息数
     *
     * @param uid  本人
     * @param ouid 对方
     * @return
     */
    public int getUnReadMsgCountWithOther(String uid, String ouid) {
        Example example = new Example(MsgStore.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("receiverUid", uid).andEqualTo("senderUid", ouid).andEqualTo("msgIsread", 0);
        return msgStoreMapper.selectCountByExample(example);
    }

    //获取聊天对象的未读消息
    @Transactional
    public List<MsgStore> getUnReadMsg(String ruid, String suid) {
        Example example = new Example(MsgStore.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("receiverUid", suid).andEqualTo("senderUid", ruid).andEqualTo("msgIsread", 0);
        example.setOrderByClause("msg_time" + " " + "asc");
        List<MsgStore> msgStores = msgStoreMapper.selectByExample(example);
        if (msgStores.size() > 0) {
            ArrayList<Integer> ids = new ArrayList<>();
            for (MsgStore msgStore : msgStores) {
                if (suid.equals(msgStore.getReceiverUid())) {
                    ids.add(msgStore.getId());
                }
            }
            if (ids.size() > 0) {
                msgStoreMapper.updateMsgReadStatus(ids);
            }
            return msgStores;
        }
        return new ArrayList<MsgStore>();
    }

    public List<MsgStore> getAllMsg(String ruid, String suid) {
        return msgStoreMapper.getAllMsg(ruid, suid);
    }

    /**
     * 获取用户与聊天对象的最近最新一条数据
     */
    public MsgStore getLastMsg(String receiveruid, String senderuid) {
        return msgStoreMapper.getLastMsg(receiveruid, senderuid);
    }

    //分页获取用户聊天信息
    @Transactional
    public PageResult<MsgStore> getNearMsg(String ruid, String suid, int page, int row) {

        //添加分页条件
        PageHelper.startPage(page, row);
        List<MsgStore> msgStores = msgStoreMapper.getAllMsg(ruid, suid);
        if (!msgStores.isEmpty()) {
            ArrayList<Integer> ids = new ArrayList<>();
            for (MsgStore msgStore : msgStores) {
                if (suid.equals(msgStore.getReceiverUid())) {
                    ids.add(msgStore.getId());
                }
            }
            PageInfo<MsgStore> pageInfo = new PageInfo<>(msgStores);
            if (!ids.isEmpty()) {
                msgStoreMapper.updateMsgReadStatus(ids);
            }
            return new PageResult<>(pageInfo, pageInfo.getList());
        }
        PageInfo<MsgStore> pageInfo = new PageInfo<>(msgStores);
        return new PageResult<>(pageInfo, pageInfo.getList());
    }
}
