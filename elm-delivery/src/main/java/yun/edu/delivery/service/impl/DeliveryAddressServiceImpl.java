package yun.edu.delivery.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yun.edu.delivery.mapper.DeliveryAddressMapper;
import yun.edu.delivery.pojo.DeliveryAddress;
import yun.edu.delivery.service.DeliveryAddressService;

import java.util.List;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {
    
    @Autowired
    private DeliveryAddressMapper deliveryAddressMapper;
    
    @Override
    public List<DeliveryAddress> listDeliveryAddressByUserId(String userId) {
        return deliveryAddressMapper.listDeliveryAddressByUserId(userId);
    }
    
    @Override
    public DeliveryAddress getDeliveryAddressById(Integer daId) {
        return deliveryAddressMapper.getDeliveryAddressById(daId);
    }
    
    @Override
    public int saveDeliveryAddress(DeliveryAddress deliveryAddress) {
        return deliveryAddressMapper.saveDeliveryAddress(deliveryAddress);
    }
    
    @Override
    public int updateDeliveryAddress(DeliveryAddress deliveryAddress) {
        return deliveryAddressMapper.updateDeliveryAddress(deliveryAddress);
    }
    
    @Override
    public int removeDeliveryAddress(Integer daId) {
        return deliveryAddressMapper.removeDeliveryAddress(daId);
    }
} 