package com.blog.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;
import javax.persistence.Transient;

@ApiModel("用户信息对象")
@Table(name = "ylb_user_info")
public class UserInfo {
    @ApiModelProperty("用户uid")
    private String uid;
    @ApiModelProperty("用户昵称")
    private String nickname;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("简介")
    private String brief;
    @ApiModelProperty("工作")
    private String work;
    @ApiModelProperty("手机")
    private String phone;
    @ApiModelProperty("qq")
    private String qq;
    @ApiModelProperty("微信")
    private String wechat;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("地址")
    private String addr;
    @ApiModelProperty("账号 非该表字段")
    @Transient
    private String username;
    @ApiModelProperty("头像 非该表字段")
    @Transient
    private String avatar;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex='" + sex + '\'' +
                ", brief='" + brief + '\'' +
                ", work='" + work + '\'' +
                ", phone='" + phone + '\'' +
                ", qq='" + qq + '\'' +
                ", wechat='" + wechat + '\'' +
                ", email='" + email + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }
}
