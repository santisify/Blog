package com.blog.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

@ApiModel("用户对象")
@Table(name = "ylb_user")
public class User {

    @ApiModelProperty("自增id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty("用户uid")
    private String uid;
    @ApiModelProperty("用户权限id")
    private Integer roleId;
    @ApiModelProperty("用户账号")
    private String username;
    @ApiModelProperty("用户密码")
    private String password;
    @ApiModelProperty("用户头像")
    private String avatar;
    @ApiModelProperty("登录时间")
    private Date loginTime;
    @ApiModelProperty("注册时间")
    private Date createTime;
    @ApiModelProperty("用户昵称")
    @Transient
    private String nickname;
    @ApiModelProperty("用户未读消息数")
    @Transient
    private Integer unreadMsg;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getUnreadMsg() {
        return unreadMsg;
    }

    public void setUnreadMsg(Integer unreadMsg) {
        this.unreadMsg = unreadMsg;
    }
}
