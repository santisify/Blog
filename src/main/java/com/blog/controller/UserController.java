package com.blog.controller;

import com.blog.pojo.Blog;
import com.blog.pojo.PageResult;
import com.blog.pojo.User;
import com.blog.pojo.UserInfo;
import com.blog.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowFansService followFansService;

    @Autowired
    private BlogCollectService collectService;

    /**
     * 获取用户文章信息
     */
    @GetMapping("/user/{uid}")
    public String userIndex(@PathVariable String uid,Model model){
        try {
            userInfoCallBack(uid,model);
            articleCount(uid, model);
            followCountCallBack(uid, model);
            fansCountCallBack(uid, model);

            PageResult<Blog> blogs = blogService.getBlogs(uid, 1,10);

            if (CollectionUtils.isEmpty(blogs.getDatas())) {
                model.addAttribute("msg", "查询失败");
            }
            model.addAttribute("blogs", blogs);
            model.addAttribute("class", "one");
            return "user/index";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "user/index";
        }
    }

    /**
     * 获取用户文章信息--分页
     */
    @GetMapping("/user/blog/{uid}/{page}/{rows}")
    public String userIndex(
            @PathVariable String uid,
            @PathVariable int page,
            @PathVariable int rows,
            Model model){
        //回填信息
        try {
            userInfoCallBack(uid, model);
            articleCount(uid, model);
            followCountCallBack(uid, model);
            fansCountCallBack(uid, model);

            PageResult<Blog> blogs = blogService.getBlogs(uid, page,rows);

            if (CollectionUtils.isEmpty(blogs.getDatas())) {
                model.addAttribute("msg", "查询失败");
            }
            model.addAttribute("blogs", blogs);
            model.addAttribute("class", "one");
            return "user/index";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "user/index";
        }
    }

    /**
     * 获取用户收藏文章
     * @return
     */
    @GetMapping("/collect/{uid}")
    public String myCollect(@PathVariable String uid,Model model){
        try {
            userInfoCallBack(uid,model);
            articleCount(uid, model);
            followCountCallBack(uid, model);
            fansCountCallBack(uid, model);

            PageResult<Blog> blogs = collectService.getBlogsByUid(uid, 1, 20);

            if (CollectionUtils.isEmpty(blogs.getDatas())) {
                model.addAttribute("msg", "查询失败");
            }
            model.addAttribute("blogs", blogs);
            model.addAttribute("class", "two");
            return "user/mycollect";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "user/mycollect";
        }
    }

    /**
     * 获取用户收藏文章---分页
     * @return
     */
    @GetMapping("/collect/{uid}/{page}/{rows}")
    public String myCollect(@PathVariable String uid,
                            @PathVariable int page,
                            @PathVariable int rows,
                            Model model){
        try {
            userInfoCallBack(uid,model);
            articleCount(uid, model);
            followCountCallBack(uid, model);
            fansCountCallBack(uid, model);

            PageResult<Blog> blogs = collectService.getBlogsByUid(uid, page, rows);

            if (CollectionUtils.isEmpty(blogs.getDatas())) {
                model.addAttribute("msg", "查询失败");
            }
            model.addAttribute("blogs", blogs);
            model.addAttribute("class", "two");
            return "user/mycollect";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "user/mycollect";
        }
    }


    /**
     * 我的收藏中点击取消收藏文章
     */
    @GetMapping("/blog/cancelcollect/{uid}/{bid}")
    public String cancelCollect(@PathVariable String uid,@PathVariable String bid,Model model){
        try {
            Boolean aBoolean = collectService.removeCollect(uid, bid);
            PageResult<Blog> blogs = collectService.getBlogsByUid(uid, 1, 20);
            model.addAttribute("blogs", blogs);
//        return "redirect:/user/"+uid;
            return "user/mycollect::collectcenter";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "user/mycollect::collectcenter";
        }
    }

    /**
     * 获取用户关注对象
     * @return
     */
    @GetMapping("/follow/{uid}")
    public String myFollow(@PathVariable String uid,Model model){
        try {
            userInfoCallBack(uid,model);
            articleCount(uid, model);
            followCountCallBack(uid, model);
            fansCountCallBack(uid, model);

            PageResult<UserInfo> follows = followFansService.getFollows(uid, 1, 20);

            if (CollectionUtils.isEmpty(follows.getDatas())) {
                model.addAttribute("msg", "查询失败");
            }
            model.addAttribute("pageInfo", follows.getPageInfo());
            model.addAttribute("follows", follows);
            model.addAttribute("class", "three");
            return "user/myfollow";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "user/myfollow";
        }
    }


    /**
     * 获取用户关注对象---分页
     * @return
     */
    @GetMapping("/follow/{uid}/{page}/{rows}")
    public String myFollow2(@PathVariable String uid,
                            @PathVariable int page,
                            @PathVariable int rows,
                            Model model){
        try {
            userInfoCallBack(uid,model);
            articleCount(uid, model);
            followCountCallBack(uid, model);
            fansCountCallBack(uid, model);

            PageResult<UserInfo> follows = followFansService.getFollows(uid, page, rows);

            if (CollectionUtils.isEmpty(follows.getDatas())) {
                model.addAttribute("msg", "查询失败");
            }
            model.addAttribute("pageInfo", follows.getPageInfo());
            model.addAttribute("follows", follows);
            model.addAttribute("class", "three");
            return "user/myfollow";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "user/myfollow";
        }
    }

    /**
     * 获取用户粉丝对象
     * @return
     */
    @GetMapping("/followfa/{uid}")
    public String myFans(@PathVariable String uid,Model model){
        try {
            userInfoCallBack(uid,model);
            articleCount(uid, model);
            followCountCallBack(uid, model);
            fansCountCallBack(uid, model);

            PageResult<UserInfo> fans = followFansService.getFans(uid, 1, 20);

            if (CollectionUtils.isEmpty(fans.getDatas())) {
                model.addAttribute("msg", "查询失败");
            }
            model.addAttribute("pageInfo", fans.getPageInfo());
            model.addAttribute("fans", fans);
            model.addAttribute("class", "four");
            return "user/myfan";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "user/myfan";
        }
    }

    /**
     * 获取用户粉丝对象
     * @return
     */
    @GetMapping("/followfa/{uid}/{page}/{rows}")
    public String myFans2(@PathVariable String uid,
                          @PathVariable int page,
                          @PathVariable int rows,
                          Model model){
        try {
            userInfoCallBack(uid,model);
            articleCount(uid, model);
            followCountCallBack(uid, model);
            fansCountCallBack(uid, model);

            PageResult<UserInfo> fans = followFansService.getFans(uid, page, rows);

            if (CollectionUtils.isEmpty(fans.getDatas())) {
                model.addAttribute("msg", "查询失败");
            }
            model.addAttribute("pageInfo", fans.getPageInfo());
            model.addAttribute("fans", fans);
            model.addAttribute("class", "four");
            return "user/myfan";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "user/myfan";
        }
    }


    /**
     * 用户详细信息
     */
    @GetMapping("userinfo/setting/{uid}")
    public String userInfoSetting(@PathVariable("uid") String uid, Model model){
        userInfoCallBack(uid, model);
        articleCount(uid, model);
        followCountCallBack(uid, model);
        fansCountCallBack(uid, model);
        model.addAttribute("class", "five");
        return "user/settings";
    }


    /**
     * 用户详细信息修改
     */
    @PostMapping("userinfo/update/{uid}")
    public String userInfoUpdate(@PathVariable("uid")String uid, UserInfo userInfo, Model model, HttpSession session){

        try {
            Boolean bool = userInfoService.updateUserInfo(uid, userInfo);
            if (bool) {
                User user = userService.getUserByUid(uid);
                if ("女".equals(userInfo.getSex())){
                    if (user.getAvatar().contains("avatar")){
                        user.setAvatar("/images/avatar/woman.png");
                        userService.updateUserByUid(uid, user);
                    }
                }else if ("男".equals(userInfo.getSex())){
                    if (user.getAvatar().contains("avatar")){
                        user.setAvatar("/images/avatar/man.png");
                        userService.updateUserByUid(uid, user);
                    }
                }
                session.setAttribute("userInfo", userInfo);
                session.setAttribute("loginUser", user);
                articleCount(uid, model);
                followCountCallBack(uid, model);
                fansCountCallBack(uid, model);
                model.addAttribute("msg", "修改成功~");
                model.addAttribute("class", "five");
            } else {
                model.addAttribute("msg", "修改失败~");
                model.addAttribute("class", "five");
            }
            return "user/settings";
        } catch (Exception e) {
            logger.info(e.getMessage());
            model.addAttribute("msg", "修改失败~");
            model.addAttribute("class", "five");
            return "user/settings";
        }
    }

    // 更新头像
    @GetMapping("/user/update-avatar/{uid}")
    public String toUpdateAvatar(@PathVariable String uid,Model model){
        // 用户信息回填
        userInfoCallBack(uid,model);
        articleCount(uid, model);
        followCountCallBack(uid, model);
        fansCountCallBack(uid, model);
        return "user/update-avatar";
    }


//    ----------------------其他作者信息----------------------------------

    /**
     * 查看作者信息
     * @return
     */
    @GetMapping("/article/{uid}")
    public String articleIndex(@PathVariable String uid,Model model){
        try {
            userInfoCallBack(uid,model);

            PageResult<Blog> blogs = blogService.getBlogs(uid, 1,10);

            if (CollectionUtils.isEmpty(blogs.getDatas())) {
                model.addAttribute("msg", "查询失败");
            }
            articleCount(uid, model);
            followCountCallBack(uid, model);
            fansCountCallBack(uid, model);
            model.addAttribute("pageInfo", blogs.getPageInfo());
            model.addAttribute("blogs", blogs);
            return "page/index";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "page/index";
        }
    }

    /**
     * 查看作者信息--分页
     * @return
     */
    @GetMapping("/article/blog/{uid}/{page}/{rows}")
    public String articleIndex(
            @PathVariable String uid,
            @PathVariable int page,
            @PathVariable int rows,
            Model model){
        try {
            //回填信息
            userInfoCallBack(uid, model);
            articleCount(uid, model);

            PageResult<Blog> blogs = blogService.getBlogs(uid, page,rows);

            if (CollectionUtils.isEmpty(blogs.getDatas())) {
                model.addAttribute("msg", "查询失败");
            }
            model.addAttribute("pageInfo", blogs.getPageInfo());
            model.addAttribute("blogs", blogs);
            return "page/index";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "page/index";
        }
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
