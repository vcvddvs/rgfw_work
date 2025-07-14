package yun.edu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import yun.edu.pojo.Food;

import java.util.List;

@Mapper
public interface FoodMapper {
    @Select("SELECT * FROM food WHERE businessId = #{businessId}")
    List<Food> getFoodsByBusinessId(@Param("businessId") int businessId);
}