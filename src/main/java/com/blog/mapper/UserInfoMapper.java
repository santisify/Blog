package com.blog.mapper;

import com.blog.pojo.UserInfo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface UserInfoMapper extends Mapper<UserInfo> {

    @Select("select nickname from ylb_user_info where uid = #{uid}")
    String getNickByUid(String uid);
}
