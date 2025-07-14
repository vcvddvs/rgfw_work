package yun.edu.delivery.mapper;

import org.apache.ibatis.annotations.*;
import yun.edu.delivery.pojo.DeliveryAddress;

import java.util.List;

@Mapper
public interface DeliveryAddressMapper {
    
    @Select("select * from deliveryaddress where userId=#{userId}")
    List<DeliveryAddress> listDeliveryAddressByUserId(String userId);
    
    @Select("select * from deliveryaddress where daId=#{daId}")
    DeliveryAddress getDeliveryAddressById(Integer daId);
    
    @Insert("insert into deliveryaddress(contactName,contactSex,contactTel,address,userId) " +
            "values(#{contactName},#{contactSex},#{contactTel},#{address},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "daId")
    int saveDeliveryAddress(DeliveryAddress deliveryAddress);
    
    @Update("update deliveryaddress set contactName=#{contactName},contactSex=#{contactSex}," +
            "contactTel=#{contactTel},address=#{address} where daId=#{daId}")
    int updateDeliveryAddress(DeliveryAddress deliveryAddress);
    
    @Delete("delete from deliveryaddress where daId=#{daId}")
    int removeDeliveryAddress(Integer daId);
} 