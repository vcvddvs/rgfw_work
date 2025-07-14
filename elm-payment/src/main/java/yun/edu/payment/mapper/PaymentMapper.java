package yun.edu.payment.mapper;

import org.apache.ibatis.annotations.*;
import yun.edu.payment.pojo.Payment;

import java.util.List;

@Mapper
public interface PaymentMapper {
    
    @Select("select * from payment where orderId=#{orderId}")
    Payment getPaymentByOrderId(Integer orderId);
    
    @Select("select * from payment where paymentId=#{paymentId}")
    Payment getPaymentById(Integer paymentId);
    
    @Insert("insert into payment(orderId,amount,paymentMethod,paymentStatus,createTime,paymentTime,transactionId) " +
            "values(#{orderId},#{amount},#{paymentMethod},#{paymentStatus},#{createTime},#{paymentTime},#{transactionId})")
    @Options(useGeneratedKeys = true, keyProperty = "paymentId")
    int createPayment(Payment payment);
    
    @Update("update payment set paymentStatus=#{paymentStatus},paymentTime=#{paymentTime}," +
            "transactionId=#{transactionId} where paymentId=#{paymentId}")
    int updatePaymentStatus(Payment payment);
    
    @Select("select * from payment where paymentStatus=#{status}")
    List<Payment> getPaymentsByStatus(String status);
} 