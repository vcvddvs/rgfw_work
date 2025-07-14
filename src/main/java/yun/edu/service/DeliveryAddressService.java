package yun.edu.service;

import yun.edu.pojo.DeliveryAddress;
import java.util.List;

public interface DeliveryAddressService {
    List<DeliveryAddress> getAddressesByUserId(String userId);
    boolean addAddress(DeliveryAddress address);
    boolean deleteAddress(Integer daId);
}