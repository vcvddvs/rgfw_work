package yun.edu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import yun.edu.pojo.Business;

import java.util.List;

@Mapper
public interface BusinessMapper {
    @Select("SELECT * FROM business")
    List<Business> getAllBusinesses();

    // 添加分页查询方法
    @Select("SELECT * FROM business LIMIT #{offset}, #{limit}")
    List<Business> getBusinessesByPage(@Param("offset") int offset, @Param("limit") int limit);
}