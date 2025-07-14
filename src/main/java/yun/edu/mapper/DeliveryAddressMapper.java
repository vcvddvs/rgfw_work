package yun.edu.mapper;

import org.apache.ibatis.annotations.*;
import yun.edu.pojo.DeliveryAddress;
import java.util.List;

@Mapper
public interface DeliveryAddressMapper {
    @Select("SELECT * FROM deliveryaddress WHERE userId = #{userId}")
    List<DeliveryAddress> getAddressesByUserId(@Param("userId") String userId);

    @Insert("INSERT INTO deliveryaddress(address, contactName, contactSex, contactTel, userId) " +
            "VALUES(#{address}, #{contactName}, #{contactSex}, #{contactTel}, #{userId})")
    int addAddress(DeliveryAddress address);

    @Delete("DELETE FROM deliveryaddress WHERE daId = #{daId}")
    int deleteAddress(@Param("daId") Integer daId);
}