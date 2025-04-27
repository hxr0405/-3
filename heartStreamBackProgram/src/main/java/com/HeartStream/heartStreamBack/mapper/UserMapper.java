package com.HeartStream.heartStreamBack.mapper;

import com.HeartStream.heartStreamBack.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{username} and password = #{password}")
    User selectByUsernameAndPassword(User user);

    @Select("select count(*) from user where username = #{username}")
    int countByUsername(String username);

    @Select("select * from user where ID = #{userId}")
    User getUserById(Long userId);

    @Select("select count(*) from user where username = #{username} and email = #{email}")
    int countByUsernameAndEmail(String username, String email);

    @Insert("insert into user (username, password, sex, email) values (#{username}, #{password}, #{sex}, #{email})")
    int insert(User user);

    @Update("update user set password = #{password} where username = #{username}")
    int updatePassword(User user);

    @Update("update user set sex = #{sex}, email = #{email} where username = #{username}")
    int updateUser(User user);

    @Update("update user set image = #{image} where id = #{ID}")
    int updateImage(User user);
}

