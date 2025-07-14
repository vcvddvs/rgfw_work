package yun.edu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yun.edu.pojo.Business;
import yun.edu.service.BusinessService;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessService businessService;

    @GetMapping("elm_servlet_war_exploded/api/businesses")
    public ResponseEntity<List<Business>> getBusinesses(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Business> businesses = businessService.getBusinessesByPage(page, size);
        return ResponseEntity.ok(businesses);
    }
}