package com.blog.controller;

import com.blog.pojo.FollowFans;
import com.blog.pojo.PageResult;
import com.blog.pojo.UserInfo;
import com.blog.service.BlogService;
import com.blog.service.FollowFansService;
import com.blog.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FollowFansController {

    private static final Logger logger = LoggerFactory.getLogger(FollowFansController.class);

    @Autowired
    private FollowFansService followFansService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 添加关注
     */
    @GetMapping("/follow/{uid}/{fid}")
    public String addFollow(@PathVariable String uid, @PathVariable String fid, Model model){
        try {
            FollowFans followFans = new FollowFans();
            followFans.setUserId(uid);
            followFans.setFollowerId(fid);
            followFans.setRelationType(1);
            followFansService.addFollow(followFans,model);
            articleCount(fid, model);
            followCountCallBack(fid, model);
            fansCountCallBack(fid, model);
            userInfoCallBack(fid, model);
//        Blog blog = blogService.getBlogByBid(bid);
//        model.addAttribute("blog", blog);
            return "/page/read::artcount";
        } catch (Exception e) {
            logger.error("关注失败"+e);
            return "/page/read::artcount";
        }
    }

    /**
     * 取消关注
     */
    @GetMapping("/follow/cancel/{uid}/{fid}")
    public String cancelFollow(@PathVariable String uid,@PathVariable String fid,Model model){
        try {
            FollowFans followFans = new FollowFans();
            followFans.setUserId(uid);
            followFans.setFollowerId(fid);
            followFans.setRelationType(1);
            followFansService.cancelFollow(followFans);
            articleCount(fid, model);
            followCountCallBack(fid, model);
            fansCountCallBack(fid, model);
            userInfoCallBack(fid, model);
//        Blog blog = blogService.getBlogByBid(bid);
//        model.addAttribute("blog", blog);

            return "/page/read::artcount";
        } catch (Exception e) {
            logger.error("取消关注失败"+e);
            return "/page/read::artcount";
        }
    }

    /**
     * 判断是否关注
     */
    @ResponseBody
    @GetMapping("/follow/isfollow/{uid}/{fid}")
    public String isFollow(@PathVariable String uid,@PathVariable String fid,Model model){
        Boolean aBoolean = followFansService.isFollow(uid, fid);
        if (aBoolean){
            return "200";
        }else {
            return "100";
        }
    }


    /**
     * 我的关注中取消取消关注
     */
    @GetMapping("/blog/cancelfollow/{uid}/{fid}")
    public String mycancelFollow(@PathVariable String uid,@PathVariable String fid,Model model){
        try {
            FollowFans followFans = new FollowFans();
            followFans.setUserId(uid);
            followFans.setFollowerId(fid);
            followFans.setRelationType(1);
            followFansService.cancelFollow(followFans);
            PageResult<UserInfo> follows = followFansService.getFollows(uid, 1, 20);
            model.addAttribute("follows", follows);
            return "/user/myfollow::followcenter";
        } catch (Exception e) {
            logger.info("我的关注中取消失败"+e);
            return "/user/myfollow::followcenter";
        }
    }

    /**
     * 我的粉丝中添加添加关注
     */
    @GetMapping("/blog/addfollow/{uid}/{fid}")
    public String myaddFollow(@PathVariable String uid,@PathVariable String fid,Model model){
        try {
            FollowFans followFans = new FollowFans();
            followFans.setUserId(uid);
            followFans.setFollowerId(fid);
            followFans.setRelationType(1);
            followFansService.addFollow(followFans,model);
            PageResult<UserInfo> follows = followFansService.getFans(uid, 1, 20);
            model.addAttribute("fans", follows);
            return "/user/myfan::fancenter";
        } catch (Exception e) {
            logger.info("我的粉丝中关注失败"+e);
            return "/user/myfan::fancenter";
        }
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

    /**
     * 个人信息页 --查询用户详细信息
     */
    public void userInfoCallBack(String uid,Model model){
        UserInfo userInfo = userInfoService.getOneUserInfoByUid(uid);
        model.addAttribute("userInfo", userInfo);
    }
}
