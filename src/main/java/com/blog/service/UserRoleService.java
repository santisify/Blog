package com.blog.service;

import com.blog.mapper.UserRoleMapper;
import com.blog.pojo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    //根据用户角色id查询用户角色
    public UserRole getRoleByid(Integer id){
        return userRoleMapper.selectByPrimaryKey(id);
    }
}
