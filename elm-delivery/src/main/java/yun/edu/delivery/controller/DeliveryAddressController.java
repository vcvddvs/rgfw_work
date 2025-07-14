package yun.edu.delivery.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import yun.edu.common.Result;
import yun.edu.delivery.pojo.DeliveryAddress;
import yun.edu.delivery.service.DeliveryAddressService;

import java.util.List;

@RestController
@RequestMapping("/deliveryAddress")
public class DeliveryAddressController {
    
    private static final Logger logger = LoggerFactory.getLogger(DeliveryAddressController.class);
    
    @Autowired
    private DeliveryAddressService deliveryAddressService;
    
    @GetMapping("/listByUserId/{userId}")
    public Result<List<DeliveryAddress>> listDeliveryAddressByUserId(@PathVariable("userId") String userId) {
        logger.info("获取用户地址列表，userId: {}", userId);
        
        List<DeliveryAddress> list = deliveryAddressService.listDeliveryAddressByUserId(userId);
        
        logger.info("查询到地址数量: {}", list != null ? list.size() : 0);
        if (list != null) {
            for (DeliveryAddress address : list) {
                logger.info("地址信息: {}", address);
            }
        }
        
        return Result.success(list);
    }
    
    @GetMapping(value = "/{daId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<DeliveryAddress> getDeliveryAddressById(@PathVariable("daId") Integer daId) {
        DeliveryAddress deliveryAddress = deliveryAddressService.getDeliveryAddressById(daId);
        if (deliveryAddress != null) {
            return Result.success(deliveryAddress);
        }
        return Result.fail("地址不存在");
    }
    
    @PostMapping("/")
    public Result<DeliveryAddress> saveDeliveryAddress(@RequestBody DeliveryAddress deliveryAddress) {
        int result = deliveryAddressService.saveDeliveryAddress(deliveryAddress);
        if (result > 0) {
            return Result.success(deliveryAddress);
        }
        return Result.fail("添加地址失败");
    }
    
    @PutMapping("/")
    public Result<Void> updateDeliveryAddress(@RequestBody DeliveryAddress deliveryAddress) {
        int result = deliveryAddressService.updateDeliveryAddress(deliveryAddress);
        if (result > 0) {
            return Result.success();
        }
        return Result.fail("修改地址失败");
    }
    
    @DeleteMapping("/{daId}")
    public Result<Void> removeDeliveryAddress(@PathVariable("daId") Integer daId) {
        int result = deliveryAddressService.removeDeliveryAddress(daId);
        if (result > 0) {
            return Result.success();
        }
        return Result.fail("删除地址失败");
    }
} 