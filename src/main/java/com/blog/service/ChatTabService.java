package com.blog.service;

import com.blog.mapper.ChatTabMapper;
import com.blog.pojo.ChatTab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ChatTabService {

    @Autowired
    private ChatTabMapper chatTabMapper;

    /**
     * 获取用户聊天对象
     *
     * @param uid
     * @return
     */
    public List<ChatTab> getObjTab(String uid) {
        Example example = new Example(ChatTab.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", uid).orEqualTo("ouid", uid);
        return chatTabMapper.selectByExample(example);
    }

    /**
     * 添加聊天标记
     *
     * @param receiveruid
     * @param senderuid
     */
    public void addChatObj(String receiveruid, String senderuid) {
        ChatTab mark = isHasChatMark(receiveruid, senderuid);
        if (mark == null) {
            ChatTab chatTab = new ChatTab();
            chatTab.setUid(senderuid);
            chatTab.setOuid(receiveruid);
            chatTabMapper.insertSelective(chatTab);
        }
    }

    /**
     * 判断是否有聊天标记
     */
    public ChatTab isHasChatMark(String receiveruid, String senderuid) {
        Example example = new Example(ChatTab.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", senderuid).andEqualTo("ouid", receiveruid).orEqualTo("uid", receiveruid).andEqualTo("ouid", senderuid);
        return chatTabMapper.selectOneByExample(example);
    }
}
