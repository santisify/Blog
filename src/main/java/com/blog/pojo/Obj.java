package com.blog.pojo;

import javax.persistence.Transient;
import java.util.Date;

public class Obj {

    private String uid;
    private String name;
    private String avatar;
    @Transient
    private String lastmsg;
    @Transient
    private Date time;
    @Transient
    private Integer msgcount;

    public Integer getMsgcount() {
        return msgcount;
    }

    public void setMsgcount(Integer msgcount) {
        this.msgcount = msgcount;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLastmsg() {
        return lastmsg;
    }

    public void setLastmsg(String lastmsg) {
        this.lastmsg = lastmsg;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
