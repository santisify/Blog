package com.blog.mapper;

import com.blog.pojo.MsgStore;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface MsgStoreMapper extends Mapper<MsgStore> {

    @Select("select id,receiver_uid,sender_uid,msg_content,msg_time,msg_type,msg_isread from ylb_msg_store where" +
            " (receiver_uid = #{receiveruid} or sender_uid = #{receiveruid}) and (sender_uid = #{senderuid} or receiver_uid = #{senderuid}) order by msg_time desc limit 1")
    MsgStore getLastMsg(@Param("receiveruid") String receiveruid, @Param("senderuid") String senderuid);

    @Select("select id,receiver_uid,sender_uid,msg_content,msg_time,msg_type,msg_isread from ylb_msg_store where" +
            " (receiver_uid = #{receiveruid} or sender_uid = #{receiveruid}) and (sender_uid = #{senderuid} or receiver_uid = #{senderuid}) order by msg_time desc")
    List<MsgStore> getAllMsg(@Param("receiveruid") String receiveruid, @Param("senderuid") String senderuid);

    //修改消息状态未已读
    void updateMsgReadStatus(List ids);
}
