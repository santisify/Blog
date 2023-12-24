package com.blog.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;

@ApiModel("关注与粉丝对象表")
@Table(name = "ylb_follow_fans")
public class FollowFans {

    @ApiModelProperty("关注者uid")
    private String userId;//用户id
    @ApiModelProperty("被关注者uid")
    private String followerId;//被关注者id
    @ApiModelProperty("关系 1关注 2粉丝")
    private Integer relationType;//关系  1 关注  2 粉丝

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public Integer getRelationType() {
        return relationType;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
    }

    @Override
    public String toString() {
        return "FollowFans{" +
                "userId='" + userId + '\'' +
                ", followerId='" + followerId + '\'' +
                ", relationType=" + relationType +
                '}';
    }
}
