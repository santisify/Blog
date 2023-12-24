package com.blog.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * 博客表
 */
@Table(name = "ylb_blog")
@ApiModel(value = "blog对象")
public class Blog {

    @ApiModelProperty("自增id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ApiModelProperty("文章id")
    private String bid;
    @ApiModelProperty("文章标题")
    private String title;
    @ApiModelProperty("文章内容")
    private String content;
    @ApiModelProperty("是否置顶")
    private Integer sort;
    @ApiModelProperty("浏览量")
    private Integer views;
    @ApiModelProperty("作者id")
    private String authorId;
    @ApiModelProperty("作者姓名")
    private String authorName;
    @ApiModelProperty("作者头像")
    private String authorAvatar;
    @ApiModelProperty("分类id")
    private Integer categoryId;
    @ApiModelProperty("分类名称")
    private String categoryName;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("修改时间")
    private Date updateTime;
    @ApiModelProperty("是否原创")
    private Integer original;
    @ApiModelProperty("收藏量 非表字段")
    @Transient
    private Integer collect;
    @ApiModelProperty("评论量 非表字段")
    @Transient
    private Integer comment;

    public Blog() {
    }

    public Blog(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getOriginal() {
        return original;
    }

    public void setOriginal(Integer original) {
        this.original = original;
    }

    public Integer getCollect() {
        return collect;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", bid='" + bid + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", sort=" + sort +
                ", views=" + views +
                ", authorId='" + authorId + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorAvatar='" + authorAvatar + '\'' +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", original=" + original +
                '}';
    }
}
