package yun.edu.rating.mapper;

import org.apache.ibatis.annotations.*;
import yun.edu.rating.pojo.Rating;

import java.util.List;

@Mapper
public interface RatingMapper {
    
    @Select("select * from rating where orderId=#{orderId}")
    Rating getRatingByOrderId(Integer orderId);
    
    @Select("select * from rating where ratingId=#{ratingId}")
    Rating getRatingById(Integer ratingId);
    
    @Insert("insert into rating(orderId,userId,businessId,foodId,deliveryRating,foodRating,content,createTime,status) " +
            "values(#{orderId},#{userId},#{businessId},#{foodId},#{deliveryRating},#{foodRating},#{content},#{createTime},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "ratingId")
    int saveRating(Rating rating);
    
    @Update("update rating set deliveryRating=#{deliveryRating},foodRating=#{foodRating},content=#{content}," +
            "status=#{status} where ratingId=#{ratingId}")
    int updateRating(Rating rating);
    
    @Select("select * from rating where userId=#{userId}")
    List<Rating> getRatingsByUserId(String userId);
    
    @Select("select * from rating where businessId=#{businessId}")
    List<Rating> getRatingsByBusinessId(Integer businessId);
    
    @Select("select * from rating where foodId=#{foodId}")
    List<Rating> getRatingsByFoodId(Integer foodId);
    
    @Select("select * from rating where status=#{status}")
    List<Rating> getRatingsByStatus(Integer status);
} 