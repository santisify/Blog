package com.blog.service;

import com.blog.mapper.BlogMapper;
import com.blog.mapper.UserInfoMapper;
import com.blog.mapper.UserMapper;
import com.blog.pojo.Blog;
import com.blog.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    public User queryUserByName(String username){
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        return userMapper.selectOneByExample(example);
    }

    /**
     * 用户注册
     * @param user
     */
    public void saveUser(User user){
        userMapper.insert(user);
    }


    /**
     * 根据用户id查询用户
     * @param uid
     * @return
     */
    public User getUserByUid(String uid){
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", uid);
        User user = userMapper.selectOneByExample(example);
        String nickname = getUserNickName(uid);
        user.setNickname(nickname);
        return user;
    }

    //获取用户昵称
    public String getUserNickName(String uid){
        return userInfoMapper.getNickByUid(uid);
    }

    @Transactional
    public void updateUserByUid(String uid,User user){
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", uid);
        userMapper.updateByExampleSelective(user, example);
        Example example1 = new Example(Blog.class);
        Blog blog = new Blog(user.getAvatar());
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("authorId",uid);
        blogMapper.updateByExampleSelective(blog, example1);
    }

    public Boolean changePwd(User user){
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", user.getUsername());
        return userMapper.updateByExampleSelective(user, example) == 1 ? true : false;
    }


}
