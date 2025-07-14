package yun.edu.business.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import yun.edu.business.config.BlobToBase64TypeHandler;
import yun.edu.business.pojo.Business;

import java.util.List;

@Mapper
public interface BusinessMapper {
    
    @Select("SELECT businessId, businessName, businessAddress, businessExplain, businessImg, orderTypeId, starPrice, deliveryPrice, remarks, ROUND(RAND() * 5, 1) as distance FROM business ORDER BY businessId LIMIT #{offset}, #{size}")
    @Results({
        @Result(property = "businessId", column = "businessId"),
        @Result(property = "businessName", column = "businessName"),
        @Result(property = "businessAddress", column = "businessAddress"),
        @Result(property = "businessExplain", column = "businessExplain"),
        @Result(property = "businessImg", column = "businessImg", jdbcType = JdbcType.LONGVARCHAR),
        @Result(property = "orderTypeId", column = "orderTypeId"),
        @Result(property = "starPrice", column = "starPrice"),
        @Result(property = "deliveryPrice", column = "deliveryPrice"),
        @Result(property = "remarks", column = "remarks"),
        @Result(property = "distance", column = "distance")
    })
    List<Business> getBusinessesByPage(@Param("offset") int offset, @Param("size") int size);
    
    @Select("SELECT businessId, businessName, businessAddress, businessExplain, businessImg, orderTypeId, starPrice, deliveryPrice, remarks, ROUND(RAND() * 5, 1) as distance FROM business WHERE businessId = #{businessId}")
    @Results({
        @Result(property = "businessId", column = "businessId"),
        @Result(property = "businessName", column = "businessName"),
        @Result(property = "businessAddress", column = "businessAddress"),
        @Result(property = "businessExplain", column = "businessExplain"),
        @Result(property = "businessImg", column = "businessImg", jdbcType = JdbcType.LONGVARCHAR),
        @Result(property = "orderTypeId", column = "orderTypeId"),
        @Result(property = "starPrice", column = "starPrice"),
        @Result(property = "deliveryPrice", column = "deliveryPrice"),
        @Result(property = "remarks", column = "remarks"),
        @Result(property = "distance", column = "distance")
    })
    Business getBusinessById(@Param("businessId") int businessId);
} 