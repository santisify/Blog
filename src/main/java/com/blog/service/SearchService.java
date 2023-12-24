package com.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.blog.mapper.BlogMapper;
import com.blog.pojo.Blog;
import com.blog.pojo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private BlogMapper blogMapper;

    /**
     * 搜索
     *
     * @return
     */
    public PageResult<Blog> getBlogsByKey(String key, int page, int rows, Model model) {

        if (StringUtils.isEmpty(key)) {

            PageHelper.startPage(page, rows);
            List<Blog> blogs = blogMapper.selectAll();

            PageInfo<Blog> pageInfo = new PageInfo<>(blogs);

            return new PageResult<>(pageInfo, pageInfo.getList());

        } else {

            List<Blog> blogs = blogMapper.searchBlogByKey(key);

            if (blogs.isEmpty()) {
                model.addAttribute("msg", "暂时没有你想要的内容~");
            }

            PageInfo<Blog> pageInfo = new PageInfo<>(blogs);

            return new PageResult<>(pageInfo, pageInfo.getList());
        }

    }
}
