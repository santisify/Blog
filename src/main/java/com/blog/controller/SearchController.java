package com.blog.controller;

import com.blog.pojo.Blog;
import com.blog.pojo.PageResult;
import com.blog.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;

    @GetMapping("/search/{page}/{rows}")
    public String searchGo(
                           @PathVariable("page") int page,
                           @PathVariable("rows") int rows,
                           @RequestParam("title") String key,
                           Model model){

        try {
            PageResult<Blog> blogs = searchService.getBlogsByKey(key, page, rows,model);
            model.addAttribute("blogs", blogs);
            model.addAttribute("key", key);
            return "search";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "search";
        }
    }

}
