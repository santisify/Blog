package com.blog.service;

import com.blog.pojo.LoginUser;
import com.blog.pojo.User;
import com.blog.pojo.UserInfo;
import com.blog.pojo.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService roleService;
    
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private MsgStoreService msgStoreService;

    @Autowired
    HttpSession session;

    /**
     * UserDetailsService接口用于返回用户相关数据。
     * 它有loadUserByUsername()方法，根据username查询用户实体，可以实现该接口覆盖该方法，实现自定义获取用户过程。
     * 该接口实现类被DaoAuthenticationProvider 类使用，用于认证过程中载入用户信息。
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户
        User user = userService.queryUserByName(username);
        //
        UserDetails userDetails = null;

        if(user == null){

            log.info("用户名或密码错误",username);
            throw new BadCredentialsException("用户名或密码错误");
        } else {
            UserInfo userInfo = userInfoService.getOneUserInfoByUid(user.getUid());
            //获取用户未读消息数

            int msgCount = msgStoreService.getUnReadMsgCount(user.getUid());
            user.setUnreadMsg(msgCount);
            session.setAttribute("userInfo", userInfo);
            session.setAttribute("loginUser",user);
            session.setAttribute("msgCount", msgCount);

            String password = user.getPassword();
            //用户角色
            Collection<GrantedAuthority> authorities = getAuthorities(user);

            userDetails = new LoginUser(user,authorities);

            // userDetails = new org.springframework.security.core.userdetails.User(username,password,
            //         true,
            //         true,
            //         true,
            //         true,authorities);

            return userDetails;
        }

    }

    //获取用户角色信息
    public Collection<GrantedAuthority> getAuthorities(User user){

        ArrayList<GrantedAuthority> authlists = new ArrayList<>();

        UserRole role = roleService.getRoleByid(user.getRoleId());
        //注意：这里每个权限前面都要加ROLE_。否在最后验证不会通过
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getRolename());

        authlists.add(authority);
        return authlists;
    }
}
