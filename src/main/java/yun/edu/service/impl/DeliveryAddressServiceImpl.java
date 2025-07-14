package yun.edu.service.impl;

import org.springframework.stereotype.Service;
import yun.edu.mapper.DeliveryAddressMapper;
import yun.edu.pojo.DeliveryAddress;
import yun.edu.service.DeliveryAddressService;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    private final DeliveryAddressMapper deliveryAddressMapper;

    @Override
    public List<DeliveryAddress> getAddressesByUserId(String userId) {
        return deliveryAddressMapper.getAddressesByUserId(userId);
    }

    @Override
    public boolean addAddress(DeliveryAddress address) {
        int result = deliveryAddressMapper.addAddress(address);
        return result > 0;
    }

    @Override
    public boolean deleteAddress(Integer daId) {
        int result = deliveryAddressMapper.deleteAddress(daId);
        return result > 0;
    }
}