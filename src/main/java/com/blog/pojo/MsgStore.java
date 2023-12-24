package com.blog.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@ApiModel("离线消息")
@Table(name = "ylb_msg_store")
public class MsgStore {
    @ApiModelProperty("消息id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty("接收方uid")
    private String receiverUid;
    @ApiModelProperty("发送方uid")
    private String senderUid;
    @ApiModelProperty("消息体")
    private String msgContent;
    @ApiModelProperty("消息时间")
    private Date msgTime;
    @ApiModelProperty("消息类型  0文本消息  1图片  2视频  3文件")
    private Integer msgType;
    @ApiModelProperty("消息是否已读 1已读 0未读")
    private Integer msgIsread;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReceiverUid() {
        return receiverUid;
    }

    public void setReceiverUid(String receiverUid) {
        this.receiverUid = receiverUid;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Date getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Date msgTime) {
        this.msgTime = msgTime;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getMsgIsread() {
        return msgIsread;
    }

    public void setMsgIsread(Integer msgIsread) {
        this.msgIsread = msgIsread;
    }
}
