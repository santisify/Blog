package com.blog.mapper;

import com.blog.pojo.Blog;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BlogMapper extends Mapper<Blog>{

//    List<Blog> getBlogsByUids(@Param("lists") List<String> lists);

    @Select("select id,bid,title,content,sort,views,author_id,author_name,author_avatar,category_id,category_name,create_time,original " +
            "from ylb_blog where match (title,content) against (#{key} in boolean mode)")
    List<Blog> searchBlogByKey(String key);

    @Select("select id,bid,title,content,sort,views,author_id,author_name,author_avatar,category_id,category_name,create_time,original" +
            " from ylb_blog yb where date_sub(CURDATE(),INTERVAL 5 DAY) <= date(create_time) order by create_time desc")
    List<Blog> getNewsBlogs();
}
