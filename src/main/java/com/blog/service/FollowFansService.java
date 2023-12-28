package com.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.blog.mapper.FollowFansMapper;
import com.blog.pojo.FollowFans;
import com.blog.pojo.PageResult;
import com.blog.pojo.User;
import com.blog.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowFansService {

    @Autowired
    private FollowFansMapper followFansMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;

    /**
     * 添加关注
     *
     * @return
     */
    @Transactional
    public void addFollow(FollowFans followFans1, Model model) {
        Example example = new Example(FollowFans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", followFans1.getUserId())
                .andEqualTo("followerId", followFans1.getFollowerId())
                .andEqualTo("relationType", 1);
        FollowFans followFans = followFansMapper.selectOneByExample(example);

        if (followFans != null) {
            model.addAttribute("msg", "已关注");
            return;
        }
        //A关注B，则A添加一个关注者   B同时增加一个粉丝
        followFansMapper.insertSelective(followFans1);
        FollowFans followFans2 = new FollowFans();
        followFans2.setUserId(followFans1.getFollowerId());
        followFans2.setFollowerId(followFans1.getUserId());
        followFans2.setRelationType(2);
        followFansMapper.insertSelective(followFans2);
    }

    /**
     * 取消关注
     */
    @Transactional
    public void cancelFollow(FollowFans followFans) {
        followFansMapper.delete(followFans);
        FollowFans followFans1 = new FollowFans();
        followFans1.setUserId(followFans.getFollowerId());
        followFans1.setFollowerId(followFans.getUserId());
        followFans1.setRelationType(2);
        followFansMapper.delete(followFans1);
    }

    /**
     * 查询用户所有关注
     */
    public List<FollowFans> getAllFollow(String uid) {
        Example example = new Example(FollowFans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", uid);
        criteria.andEqualTo("relationType", 1);
        return followFansMapper.selectByExample(example);
    }

    /**
     * 查询用户所有粉丝
     */
    public List<FollowFans> getAllFans(String uid) {
        Example example = new Example(FollowFans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", uid);
        criteria.andEqualTo("relationType", 2);
        return followFansMapper.selectByExample(example);
    }

    /**
     * 查询用户所有关注数
     */
    public int getFollowCount(String uid) {
        Example example = new Example(FollowFans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", uid).andEqualTo("relationType", 1);
        return followFansMapper.selectCountByExample(example);
    }

    /**
     * 查询用户所有粉丝数
     */
    public int getFansCount(String uid) {
        Example example = new Example(FollowFans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", uid).andEqualTo("relationType", 2);
        return followFansMapper.selectCountByExample(example);
    }

    /**
     * 查看是否关注
     */
    public Boolean isFollow(String uid, String fid) {
        Example example = new Example(FollowFans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", uid).andEqualTo("followerId", fid).andEqualTo("relationType", 1);
        FollowFans fans = followFansMapper.selectOneByExample(example);
        if (fans != null) {
            return true;
        }
        return false;
    }

    /**
     * 根据用户id查询该用户关注人信息
     */
    public PageResult<UserInfo> getFollows(String uid, int page, int rows) {
        Example example = new Example(FollowFans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", uid).andEqualTo("relationType", 1);

        //添加分页条件
        PageHelper.startPage(page, rows);
        List<FollowFans> follows = followFansMapper.selectByExample(example);

        ArrayList<UserInfo> userInfos = new ArrayList<>();
        for (FollowFans follow : follows) {
            UserInfo userInfo = userInfoService.getOneUserInfoByUid(follow.getFollowerId());
            User user = userService.getUserByUid(userInfo.getUid());
            userInfo.setUsername(user.getUsername());
            userInfo.setAvatar(user.getAvatar());
            userInfos.add(userInfo);
        }

        PageInfo<FollowFans> pageInfo = new PageInfo<>(follows);
        PageInfo<UserInfo> pageInfo1 = new PageInfo<>(userInfos);

        return new PageResult<>(pageInfo, pageInfo1.getList());
    }


    /**
     * 根据用户id查询该用户所有粉丝信息
     */
    public PageResult<UserInfo> getFans(String uid, int page, int rows) {
        Example example = new Example(FollowFans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("followerId", uid).andEqualTo("relationType", 1);

        //添加分页条件
        PageHelper.startPage(page, rows);
        List<FollowFans> fans = followFansMapper.selectByExample(example);

        ArrayList<UserInfo> userInfos = new ArrayList<>();
        for (FollowFans fan : fans) {
            UserInfo userInfo = userInfoService.getOneUserInfoByUid(fan.getUserId());
            User user = userService.getUserByUid(userInfo.getUid());
            userInfo.setUsername(user.getUsername());
            userInfo.setAvatar(user.getAvatar());
            userInfos.add(userInfo);
        }

        //两个pageinfo用于保存分页信息与关注用户个人信息
        PageInfo<FollowFans> pageInfo = new PageInfo<>(fans);
        PageInfo<UserInfo> pageInfo1 = new PageInfo<>(userInfos);

        return new PageResult<>(pageInfo, pageInfo1.getList());
    }

}
