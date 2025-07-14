package yun.edu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import yun.edu.pojo.User;

@Mapper
public interface LoginMapper {

    @Select("SELECT * FROM user WHERE userId = #{userId} AND password = #{password}")
    User getPandW(String userId, String password);

    @Insert("INSERT INTO user (userId, password) VALUES (#{userId}, #{password})")
    void insertUser(User user);
}