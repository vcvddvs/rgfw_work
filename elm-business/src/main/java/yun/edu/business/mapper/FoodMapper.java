package yun.edu.business.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import yun.edu.business.pojo.Food;

import java.util.List;

@Mapper
public interface FoodMapper {
    
    @Select("SELECT foodId, foodName, foodExplain, foodImg, CAST(foodPrice AS DECIMAL(10,2)) as foodPrice, businessId, remarks FROM food WHERE businessId = #{businessId}")
    @Results({
        @Result(property = "foodId", column = "foodId"),
        @Result(property = "foodName", column = "foodName"),
        @Result(property = "foodExplain", column = "foodExplain"),
        @Result(property = "foodImg", column = "foodImg"),
        @Result(property = "foodPrice", column = "foodPrice"),
        @Result(property = "businessId", column = "businessId"),
        @Result(property = "remarks", column = "remarks")
    })
    List<Food> getFoodsByBusinessId(@Param("businessId") int businessId);
    
    @Select("SELECT foodId, foodName, foodExplain, foodImg, CAST(foodPrice AS DECIMAL(10,2)) as foodPrice, businessId, remarks FROM food WHERE foodId = #{foodId}")
    @Results({
        @Result(property = "foodId", column = "foodId"),
        @Result(property = "foodName", column = "foodName"),
        @Result(property = "foodExplain", column = "foodExplain"),
        @Result(property = "foodImg", column = "foodImg"),
        @Result(property = "foodPrice", column = "foodPrice"),
        @Result(property = "businessId", column = "businessId"),
        @Result(property = "remarks", column = "remarks")
    })
    Food getFoodById(@Param("foodId") int foodId);
} 