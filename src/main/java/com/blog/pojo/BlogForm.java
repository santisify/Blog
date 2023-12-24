package com.blog.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("提交文章对象")
public class BlogForm {

    @ApiModelProperty("文章标题")
    private String title;
    @ApiModelProperty("文章内容")
    private String content;
    @ApiModelProperty("分类id")
    private Integer categoryId;
    @ApiModelProperty("作者uid")
    private String authorId;
    @ApiModelProperty("作者账号")
    private String authorName;
    @ApiModelProperty("作者头像")
    private String authorAvatar;
    @ApiModelProperty("是否原创")
    private Integer original;

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public Integer getOriginal() {
        return original;
    }

    public void setOriginal(Integer original) {
        this.original = original;
    }

    @Override
    public String toString() {
        return "BlogForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", categoryId=" + categoryId +
                ", authorId='" + authorId + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorAvatar='" + authorAvatar + '\'' +
                ", original=" + original +
                '}';
    }
}
