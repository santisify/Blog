package com.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.blog.pojo.*;
import com.blog.service.*;
import com.blog.util.FileUploadUtils;
import com.blog.util.YlbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class BlogController {

    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    private CateGoryService cateGoryService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BlogCollectService collectService;

    @Autowired
    private FollowFansService followFansService;
    
    @GetMapping("/blog/write")
    public String toWriteBlog(Model model){
        List<BlogCategory> categoryList = cateGoryService.getCategory();
        model.addAttribute("categoryList", categoryList);
        return "blog/write";
    }

    /**
     * 保存文章
     * @return
     */
    @PostMapping("/blog/write")
    public String saveBlog(BlogForm blogForm,Model model){
        //构建文章对象
        Blog blog = new Blog();
        blog.setBid(YlbUtils.getUuid());
        blog.setTitle(blogForm.getTitle());
        blog.setContent(blogForm.getContent());
        blog.setSort(0);
        blog.setViews(0);
        blog.setAuthorId(blogForm.getAuthorId());
        blog.setAuthorName(blogForm.getAuthorName());
        blog.setAuthorAvatar(blogForm.getAuthorAvatar());
        blog.setCategoryId(blogForm.getCategoryId());
        blog.setCategoryName(cateGoryService.getCategoryById(blogForm.getCategoryId()).getCategory());
        blog.setCreateTime(YlbUtils.getTime());
        blog.setUpdateTime(YlbUtils.getTime());
        blog.setOriginal(blogForm.getOriginal());
        Boolean aBoolean = blogService.savaBlog(blog);

        if (aBoolean){
            model.addAttribute("msg", "发布成功~");
        }else {
            model.addAttribute("msg", "发布失败！");
        }

        return "blog/success";
    }

    /**
     * 删除文章
     */
    @GetMapping("/blog/del/{uid}/{bid}")
    public String delBlog(@PathVariable String uid,@PathVariable String bid,Model model){
        Blog blog = blogService.getBlogByBid(bid);
        if (!blog.getAuthorId().equals(uid)) {
//            非法删除
            return "redirect:/user/"+uid;
        }
        Boolean delboolean = blogService.delBlog(bid,uid);
        PageResult<Blog> blogs = blogService.getBlogs(uid,1, 10);
        model.addAttribute("blogs", blogs);
//        return "redirect:/user/"+uid;
        return "user/index::blogcenter";
    }

    /**
     * 阅读文章
     * @return
     */
    @GetMapping("/blog/read/{bid}/{uid}")
    public String blogRead(@PathVariable String bid,@PathVariable String uid, Blog blog,Model model){
        blog = blogService.getBlogByBid(bid);
        if (blog == null) {
            model.addAttribute("msg", "查询失败");
            return "page/read";
        }else {
            //修改阅读数
            blog.setViews(blog.getViews()+1);
            blogService.updateViews(bid, blog);
            //回显文章数
            articleCount(uid, model);
            //回显关注数
            followCountCallBack(uid, model);
            //回显粉丝数
            fansCountCallBack(uid, model);
            //回显用户信息
            userInfoCallBack(uid, model);
            //回显评论
            commentrCollBack(bid, model);
            //回显收藏数
            collectCountCallBack(bid, model);
            model.addAttribute("blog", blog);
            return "page/read";
        }
    }

    /**
     * 添加收藏
     */
    @PostMapping("/blog/collect")
    @ResponseBody
    public String addCollect(BlogCollect collect){
        Boolean aBoolean = collectService.addCollect(collect);
        if (aBoolean) {
            return "200";//成功
        }else {
            return "100";//失败
        }
    }

    /**
     * 移除收藏
     */
    @GetMapping("/blog/recollect/{uid}/{bid}")
    @ResponseBody
    public String removeCollect(@PathVariable String uid,@PathVariable String bid){
        Boolean aBoolean = collectService.removeCollect(uid, bid);
        if (aBoolean) {
            return "200";//成功
        }else {
            return "100";//失败
        }
    }


    /**
     * 是否收藏
     */
    @GetMapping("/blog/iscollect/{uid}/{bid}")
    public String isCollected(@PathVariable String uid,@PathVariable String bid,Model model){
        Boolean aBoolean = collectService.isCollected(uid, bid);
        Blog blog = blogService.getBlogByBid(bid);
        if (aBoolean) {
            model.addAttribute("isCollect", aBoolean);
            model.addAttribute("blog", blog);
            collectCountCallBack(bid, model);
            return "page/read::iscollect";
        }else {
            model.addAttribute("isCollect", aBoolean);
            model.addAttribute("blog", blog);
            collectCountCallBack(bid, model);
            return "page/read::iscollect";
        }
    }

    /**
     * 编辑文章
     */
    @GetMapping("/blog/editor/{uid}/{bid}")
    public String editorBlog(@PathVariable String uid,@PathVariable String bid,Model model){
        Blog blog = blogService.getBlogByBid(bid);
        List<BlogCategory> categoryList = cateGoryService.getCategory();
        model.addAttribute("blog", blog);
        model.addAttribute("categoryList", categoryList);
        return "blog/editor";
    }

    /**
     * 保存编辑文章
     */
    @PostMapping("/blog/editor")
    public String saveEditorBlog(Blog blog,Model model){

        //构建文章对象
        blog.setCategoryName(cateGoryService.getCategoryById(blog.getCategoryId()).getCategory());
        blog.setUpdateTime(YlbUtils.getTime());
        Boolean aBoolean = blogService.updateBlog(blog);

        if (aBoolean){
            model.addAttribute("msg", "修改成功~");
        }else {
            model.addAttribute("msg", "修改失败！");
        }
        return "blog/success";
    }

    /**
     *
     */
    @GetMapping("/blog/follow/{bid}/{uid}")
    public String loginedFollow(@PathVariable String bid,@PathVariable String uid,Blog blog,Model model) {
        try {
            blog = blogService.getBlogByBid(bid);
            if (blog == null) {
                model.addAttribute("msg", "查询失败");
                return "page/read";
            } else {
                //回显文章数
                articleCount(uid, model);
                //回显关注数
                followCountCallBack(uid, model);
                //回显粉丝数
                fansCountCallBack(uid, model);
                //回显用户信息
                userInfoCallBack(uid, model);
                //回显评论
                commentrCollBack(bid, model);
                //回显收藏数
                collectCountCallBack(bid, model);
                model.addAttribute("blog", blog);
                return "page/read";
            }
        } catch (Exception e) {
            model.addAttribute("msg", "查询失败");
            logger.error("查询用户关注失败"+e);
            return "page/read";
        }
    }

    /**
     * 离线 / 他人 阅读文章
     * @return
     */
    @GetMapping("/article/read/{bid}/{uid}")
    public String blogArticleRead(@PathVariable String bid,@PathVariable String uid, Blog blog,Model model){
        try {
            blog = blogService.getBlogByBid(bid);
            if (blog == null) {
                model.addAttribute("msg", "查询失败");
                return "page/read";
            }else {
                //修改阅读数
                blog.setViews(blog.getViews()+1);
                blogService.updateViews(bid, blog);
                //回显文章数
                articleCount(uid, model);
                //回显关注数
                followCountCallBack(uid, model);
                //回显粉丝数
                fansCountCallBack(uid, model);
                //回显用户信息
                userInfoCallBack(uid, model);
                //回显评论
                commentrCollBack(bid, model);
                //回显收藏数
                collectCountCallBack(bid, model);
                model.addAttribute("blog", blog);

                return "page/read";
            }
        } catch (Exception e) {
            model.addAttribute("msg", "查询失败");
            logger.info("读取文章失败"+e.getMessage());
            return "page/read";
        }
    }

    /**
     * md图片上传
     */
    @RequestMapping("/blog/upload")
    @ResponseBody
    public JSONObject fileUpload(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file, HttpServletRequest request) throws IOException {

        try {
            String path = BlogConfig.getUploadPath();

            String pathName = FileUploadUtils.upload(path, file);

            //给editormd进行回调
            JSONObject res = new JSONObject();
            res.put("url",pathName);
            res.put("success", 1);
            res.put("message", "upload success!");
            return res;
        } catch (IOException e) {
            logger.error("上传失败",e);
        }
        return null;
    }

//************************************************************
    /**
     * 回显收藏数
     */
    public void collectCountCallBack(String bid,Model model){
        int collectCount = collectService.getCollectCountByBid(bid);
        model.addAttribute("collectCount", collectCount);
    }


    /**
     * 回显评论
     */
    public void commentrCollBack(String bid,Model model){
        List<BlogComment> commentList = commentService.getCommentsByBid(bid);
        model.addAttribute("commentList", commentList);
    }

    /**
     * 个人信息页 --查询用户详细信息
     */
    public void userInfoCallBack(String uid,Model model){
        UserInfo userInfo = userInfoService.getOneUserInfoByUid(uid);
        model.addAttribute("userInfo", userInfo);
    }

    /**
     * 回显文章数
     */
    public void articleCount(String uid,Model model){
        int articleCount = blogService.getArticleCount(uid);
        model.addAttribute("articleCount", articleCount);
    }

    /**
     * 回显关注数
     */
    public void followCountCallBack(String uid, Model model){
        int followCount = followFansService.getFollowCount(uid);
        model.addAttribute("followCount", followCount);
    }

    /**
     * 回显粉丝数
     */
    public void fansCountCallBack(String uid, Model model){
        int fansCount = followFansService.getFansCount(uid);
        model.addAttribute("fansCount", fansCount);
    }
}
