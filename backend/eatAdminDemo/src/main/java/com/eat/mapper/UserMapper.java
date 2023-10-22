package com.eat.mapper;

import com.eat.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Insert("insert into user(id,nickname,username,password,cookstyle,requirements,allergy,avatar) values (#{id},#{nickname},#{username},#{password},#{cookStyle},#{requirements},#{allergy},#{avatar})")
    int registerUser(User user);

    @Select("select * from user where username = #{username}")
    User findUserByUserName(String username);

    @Update("update user set avatar = #{avatar} where username = #{username}")
    int updateAvatar(String avatar,String username);
}
