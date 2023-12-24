package com.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.blog.mapper.BlogMapper;
import com.blog.mapper.CollectMapper;
import com.blog.pojo.Blog;
import com.blog.pojo.BlogCollect;
import com.blog.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogCollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private CommentService commentService;

    /**
     * 添加收藏
     */
    public Boolean addCollect(BlogCollect collect){
        return collectMapper.insertSelective(collect) == 1;
    }

    /**
     * 移除收藏
     */
    public Boolean removeCollect(String uid,String bid){
        Example example = new Example(BlogCollect.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", uid).andEqualTo("bid", bid);
        return collectMapper.deleteByExample(example) == 1;
    }

    /**
     * 查找文章收藏数
     */
    public int getCollectCountByBid(String bid){
        Example example = new Example(BlogCollect.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bid", bid);
        return collectMapper.selectCountByExample(example);
    }

    /**
     * 查询该用户是否收藏该文章
     */
    public Boolean isCollected(String uid,String bid){
        Example example = new Example(BlogCollect.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", uid).andEqualTo("bid", bid);
        BlogCollect blogCollect = collectMapper.selectOneByExample(example);
        //如果不为nul 怎已收藏
        if (blogCollect != null) {
            return true;
        }
        return false;
    }

    public List<BlogCollect> getCollectByBid(String bid){
        Example example = new Example(BlogCollect.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bid", bid);
        return collectMapper.selectByExample(example);
    }


    /**
     * 根据uid查询该用户手藏文章
     * @param uid
     * @return
     */
    public PageResult<Blog> getBlogsByUid(String uid,int page,int rows){
        Example example = new Example(BlogCollect.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", uid);

        //添加分页条件
        PageHelper.startPage(page, rows);
        List<BlogCollect> collects = collectMapper.selectByExample(example);

        //新建文章集合
        ArrayList<Blog> blogs = new ArrayList<>();
        //获取文章收藏数和评论数
        for (BlogCollect collect : collects) {
            Example example1 = new Example(BlogCollect.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("bid", collect.getBid());
            //根据文章id查询该文章
            Blog blog = blogService.getBlogByBid(collect.getBid());
            //查询该文章收藏数
            int i = collectMapper.selectCountByExample(example1);
            blog.setCollect(i);
            //查询该文章评论数
            int commentCount = commentService.getCommentCount(collect.getBid());
            blog.setComment(commentCount);
            blogs.add(blog);
        }
        //包装为pageinfo
        PageInfo<BlogCollect> pageInfo = new PageInfo<>(collects);//第一个pageinfo用于保存分页信息
        PageInfo<Blog> pageInfo1 = new PageInfo<>(blogs);//第二个分页用于保存文章信息
        //包装为结果集返回

        return new PageResult<>(pageInfo,pageInfo1.getList());
    }


    /**
     * 根据文章id查询文章
     */
    public Blog getBlogByBid(String bid){
        Example example = new Example(Blog.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bid", bid);
        return blogMapper.selectOneByExample(example);
    }
}
