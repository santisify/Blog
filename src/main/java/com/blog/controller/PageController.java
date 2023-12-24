package com.blog.controller;

import com.blog.pojo.Blog;
import com.blog.pojo.Download;
import com.blog.pojo.FollowFans;
import com.blog.pojo.PageResult;
import com.blog.service.BlogService;
import com.blog.service.DownloadService;
import com.blog.service.FollowFansService;
import com.blog.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {

    private static final Logger logger = LoggerFactory.getLogger(PageController.class);

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    FollowFansService followFansService;

    @Autowired
    private DownloadService downloadService;


    /**
     * 发现页  第一次
     */
    @GetMapping("/discover")
    public String pageDiscover(Model model){
        PageResult<Blog> blogs = blogService.getAllBlogs(1, 20);
        model.addAttribute("blogs", blogs);
        return "page/discover";
    }

    /**
     * 发现页  分页
     */
    @GetMapping("/discover/{page}/{rows}")
    public String pageDiscover(
            @PathVariable int page,
            @PathVariable int rows,
            Model model){
        PageResult<Blog> blogs = blogService.getAllBlogs(page, rows);
        model.addAttribute("blogs", blogs);
        return "page/discover";
    }

    /**
     * 资源下载页
     */
    @GetMapping("/download")
    public String pageDownload(Model model){
        List<Download> downloadList = downloadService.getDownSources();
        model.addAttribute("downloadList",downloadList);
        return "page/download";
    }

    /**
     * 关注页
     */
    @GetMapping("/tofollow")
    public String pageFollow(){
        return "page/follows";
    }

    @GetMapping("/tofollow/{uid}")
    public String pageFollow1(@PathVariable String uid,
                              Model model){
        try {
            List<FollowFans> follows = followFansService.getAllFollow(uid);
            ArrayList<String> list = new ArrayList<>();
            for (FollowFans follow : follows) {
                list.add(follow.getFollowerId());
            }
            PageResult<Blog> blogs = blogService.getBlogsByUids(list,1,20);
            model.addAttribute("blogs", blogs);
            return "page/follows";
        } catch (Exception e) {
            logger.info("失败"+e.getMessage());
            return "page/follows";
        }
    }

    /**
     *  关注页-----分页
     * @return
     */
    @GetMapping("/tofollow/{uid}/{page}/{rows}")
    public String pageFollow2(@PathVariable String uid,
                              @PathVariable int page,
                              @PathVariable int rows,
                              Model model){
        try {
            List<FollowFans> follows = followFansService.getAllFollow(uid);
            ArrayList<String> list = new ArrayList<>();
            for (FollowFans follow : follows) {
                list.add(follow.getFollowerId());
            }
            PageResult<Blog> blogs = blogService.getBlogsByUids(list,page,rows);
            model.addAttribute("blogs", blogs);
            return "page/follows";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "page/follows";
        }
    }

    /**
     * 最新内容
     */
    @GetMapping("/newblog")
    public String lastBlog(Model model){
        try {
            PageResult<Blog> blogs = blogService.getNewsBlogs(1,20);
            model.addAttribute("blogs", blogs);
            model.addAttribute("class", "two");
            return "page/newblog";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "page/newblog";
        }
    }

    /**
     * 最新内容---分页
     */
    @GetMapping("/newblog/{page}/{rows}")
    public String lastBlog(@PathVariable int page,@PathVariable int rows, Model model){
        try {
            PageResult<Blog> blogs = blogService.getNewsBlogs(page,rows);
            model.addAttribute("blogs", blogs);
            model.addAttribute("class", "two");
            return "page/newblog";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "page/newblog";
        }
    }

    /**
     * 最新内容
     */
    @GetMapping("/interest")
    public String interest(Model model){
        try {
            PageResult<Blog> blogs = blogService.getNewsBlogs(1,20);
            model.addAttribute("blogs", blogs);
            model.addAttribute("class", "four");
            return "page/interest";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "page/interest";
        }
    }

    /**
     * 最新内容---分页
     */
    @GetMapping("/interest/{page}/{rows}")
    public String interest(@PathVariable int page,@PathVariable int rows, Model model){
        try {
            PageResult<Blog> blogs = blogService.getNewsBlogs(page,rows);
            model.addAttribute("blogs", blogs);
            model.addAttribute("class", "four");
            return "page/interest";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "page/interest";
        }
    }

}
