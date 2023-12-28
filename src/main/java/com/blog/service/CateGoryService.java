package com.blog.service;

import com.blog.mapper.CateGoryMapper;
import com.blog.pojo.BlogCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CateGoryService {

    private final CateGoryMapper cateGoryMapper;

    public CateGoryService(CateGoryMapper cateGoryMapper) {
        this.cateGoryMapper = cateGoryMapper;
    }

    /**
     * 查询分类集合
     *
     * @return
     */
    public List<BlogCategory> getCategory() {
        return cateGoryMapper.selectAll();
    }

    /**
     * 根据id查询文章
     *
     * @param id
     */
    public BlogCategory getCategoryById(Integer id) {
        return cateGoryMapper.selectByPrimaryKey(id);
    }
}
