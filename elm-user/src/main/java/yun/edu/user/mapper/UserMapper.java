package yun.edu.user.mapper;

import org.apache.ibatis.annotations.*;
import yun.edu.user.pojo.User;

@Mapper
public interface UserMapper {
    
    @Select("<script>" +
            "SELECT * FROM user WHERE userId = #{userId}" +
            "<if test='password != null'> AND password = #{password}</if>" +
            "</script>")
    User getUserByIdAndPassword(@Param("userId") String userId, @Param("password") String password);
    
    @Insert("INSERT INTO user (userId, password, userName, userSex) VALUES (#{userId}, #{password}, #{userName}, #{userSex})")
    int insertUser(User user);
    
    @Select("SELECT * FROM user WHERE userId = #{userId}")
    User getUserById(@Param("userId") String userId);
    
    @Select("select * from user where userName=#{username}")
    User getUserByUsername(String username);
    
    @Insert("insert into user(userName,password,userSex,userImg,userEmail,userMobile) " +
            "values(#{userName},#{password},#{userSex},#{userImg},#{userEmail},#{userMobile})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int saveUser(User user);
    
    @Update("update user set userName=#{userName},password=#{password},userSex=#{userSex}," +
            "userImg=#{userImg},userEmail=#{userEmail},userMobile=#{userMobile} where userId=#{userId}")
    int updateUser(User user);
} 