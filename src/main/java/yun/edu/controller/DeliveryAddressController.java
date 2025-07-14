package yun.edu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yun.edu.pojo.DeliveryAddress;
import yun.edu.service.DeliveryAddressService;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("elm_servlet_war_exploded/api/deliveryaddresses")
@RequiredArgsConstructor
public class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;

    @GetMapping
    public ResponseEntity<List<DeliveryAddress>> getAddressesByUserId(@RequestParam String userId) {
        List<DeliveryAddress> addresses = deliveryAddressService.getAddressesByUserId(userId);
        return ResponseEntity.ok(addresses);
    }

    @PostMapping
    public ResponseEntity<Boolean> addAddress(@RequestBody DeliveryAddress address) {
        boolean added = deliveryAddressService.addAddress(address);
        return ResponseEntity.ok(added);
    }

    @DeleteMapping("{daId}")
    public ResponseEntity<Boolean> deleteAddress(@PathVariable Integer daId) {
        boolean deleted = deliveryAddressService.deleteAddress(daId);
        return ResponseEntity.ok(deleted);
    }
}