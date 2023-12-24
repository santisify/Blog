package com.blog.service;

import com.blog.mapper.CommentMapper;
import com.blog.pojo.BlogComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 根据文章id查询文章评论
     *
     * @return
     */
    public List<BlogComment> getCommentsByBid(String bid) {
        Example example = new Example(BlogComment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("topicId", bid);
        //排序
        example.setOrderByClause("create_time" + " " + "desc");
        return commentMapper.selectByExample(example);
    }

    /**
     * 添加评论
     *
     * @comment BlogComment
     */
    public Boolean addComment(BlogComment comment) {
        return commentMapper.insert(comment) == 1;
    }

    /**
     * 删除评论
     */
    public Boolean delCommentByCid(String cid) {
        Example example = new Example(BlogComment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("commentId", cid);
        return commentMapper.deleteByExample(example) == 1;
    }

    /**
     * 根据文章id查询该文章评论数
     */
    public int getCommentCount(String bid) {
        Example example = new Example(BlogComment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("topicId", bid);
        return commentMapper.selectCountByExample(example);
    }
}
