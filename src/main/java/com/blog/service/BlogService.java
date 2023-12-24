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
public class BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private CommentService commentService;

    public PageResult<Blog> getAllBlogs(int page, int rows) {
        Example example = new Example(Blog.class);
        PageHelper.startPage(page, rows);
        example.setOrderByClause("views" + " " + "desc");
        List<Blog> blogs = blogMapper.selectByExample(example);

        for (Blog blog : blogs) {
            int commentCount = commentService.getCommentCount(blog.getBid());
            blog.setComment(commentCount);
        }

        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);

        return new PageResult<>(pageInfo, pageInfo.getList());
    }

    /**
     * 保存文章
     *
     * @param blog
     */
    public Boolean savaBlog(Blog blog) {
        return blogMapper.insertSelective(blog) == 1;
    }

    /**
     * 修改文章
     */
    public Boolean updateBlog(Blog blog) {
        Example example = new Example(blog.getClass());
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bid", blog.getBid());
        return blogMapper.updateByExampleSelective(blog, example) == 1;
    }

    /**
     * 删除文章
     */
    public Boolean delBlog(String bid, String uid) {
        Example example = new Example(Blog.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bid", bid).andEqualTo("authorId", uid);
        return blogMapper.deleteByExample(example) == 1;
    }

    /**
     * 根据用户id查询该用户所有文章
     *
     * @param uid
     * @return
     */
    public PageResult<Blog> getBlogs(String uid, int page, int rows) {
        Example example = new Example(Blog.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("authorId", uid);
//        //添加模糊查询
//        if (StringUtils.isNotBlank(condition.getKey())) {
//            criteria.andLike("title", condition.getKey());
//        }
        //添加分页条件
        PageHelper.startPage(page, rows);
        example.setOrderByClause("create_time" + " " + "desc");

        List<Blog> blogs = blogMapper.selectByExample(example);


        //获取用户文章收藏数和评论数
        for (Blog blog : blogs) {
            Example example1 = new Example(BlogCollect.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("bid", blog.getBid());
            int i = collectMapper.selectCountByExample(example1);
            blog.setCollect(i);
            int commentCount = commentService.getCommentCount(blog.getBid());
            blog.setComment(commentCount);
        }
        //包装为pageinfo
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        //包装为结果集返回

        return new PageResult<>(pageInfo, pageInfo.getList());
    }

    /**
     * 根据文章id查询文章
     */
    public Blog getBlogByBid(String bid) {
        Example example = new Example(Blog.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bid", bid);
        return blogMapper.selectOneByExample(example);
    }

    /**
     * 修改阅读数
     */
    public void updateViews(String bid, Blog blog) {
        Example example = new Example(Blog.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bid", bid);
        blogMapper.updateByExampleSelective(blog, example);
    }

    /**
     * 获取用户文章数
     *
     * @param uid
     * @return
     */
    public int getArticleCount(String uid) {
        Example example = new Example(Blog.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("authorId", uid);
        return blogMapper.selectCountByExample(example);
    }

    /**
     * 根据用户id集合查询所有文章
     *
     * @return
     */
    public PageResult<Blog> getBlogsByUids(List<String> list, int page, int rows) {
        if (!list.isEmpty()) {
            Example example = new Example(Blog.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andIn("authorId", list);

            PageHelper.startPage(page, rows);
            List<Blog> blogs = blogMapper.selectByExample(example);
            //获取用户文章收藏数和评论数
            for (Blog blog : blogs) {
                Example example1 = new Example(BlogCollect.class);
                Example.Criteria criteria1 = example1.createCriteria();
                criteria1.andEqualTo("bid", blog.getBid());
                int i = collectMapper.selectCountByExample(example1);
                blog.setCollect(i);
                int commentCount = commentService.getCommentCount(blog.getBid());
                blog.setComment(commentCount);
            }

            PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
            return new PageResult<>(pageInfo, pageInfo.getList());
        }
        List<Blog> blogs = new ArrayList<>();
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        return new PageResult<>(pageInfo, pageInfo.getList());
    }


    /**
     * 获取最新文章
     */
    public PageResult<Blog> getNewsBlogs(int page, int rows) {

        PageHelper.startPage(page, rows);
        List<Blog> blogs = blogMapper.getNewsBlogs();
        for (Blog blog : blogs) {
            int commentCount = commentService.getCommentCount(blog.getBid());
            blog.setComment(commentCount);
        }
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        return new PageResult<>(pageInfo, pageInfo.getList());
    }

    /**
     * 根据分类获取文章
     */
    public PageResult<Blog> getBlogsByCategory(int page, int rows) {
        Example example = new Example(Blog.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("categoryId", "2").orEqualTo("categoryId", "4");
        PageHelper.startPage(page, rows);
        List<Blog> blogs = blogMapper.selectByExample(example);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        return new PageResult<>(pageInfo, pageInfo.getList());
    }
}
