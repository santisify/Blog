package com.blog.service;

import com.blog.mapper.UserInfoMapper;
import com.blog.mapper.UserMapper;
import com.blog.pojo.User;
import com.blog.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    /**
     * 用户注册时注册详细信息
     *
     * @param userInfo
     */
    public void SaveUserInfo(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }

    /**
     * 根据用户uid查询用户详细信息
     *
     * @param uid
     * @return
     */
    public UserInfo getOneUserInfoByUid(String uid) {

        Example example = new Example(UserInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", uid);
        UserInfo userInfo = userInfoMapper.selectOneByExample(example);
        User user = userService.getUserByUid(userInfo.getUid());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setUsername(user.getUsername());
        return userInfo;
    }

    /**
     * 修改用户详细信息
     */
    public Boolean updateUserInfo(String uid, UserInfo userInfo) {
        Example example = new Example(userInfo.getClass());
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", uid);
        return 1 == userInfoMapper.updateByExampleSelective(userInfo, example) ? true : false;
    }

    /**
     * 根据用户名和邮箱查询用户是否存在
     */
    @Transactional
    public Boolean isExistByUnameAndEmail(String username, String email) {
        //先根据账号查询用户id
        Example example1 = new Example(User.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("username", username);
        User user = userMapper.selectOneByExample(example1);
        //根据id查询该用户邮箱是否一致
        Example example = new Example(UserInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", user.getUid()).andEqualTo("email", email);
        UserInfo u = userInfoMapper.selectOneByExample(example);
        if (u != null) {
            return true;
        }
        return false;
    }
}
