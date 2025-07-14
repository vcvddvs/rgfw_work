package yun.edu.business.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yun.edu.business.pojo.Business;
import yun.edu.business.service.BusinessService;
import yun.edu.common.pojo.Result;

import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {

    private static final Logger logger = LoggerFactory.getLogger(BusinessController.class);
    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/list")
    public Result<List<Business>> getBusinesses(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("Fetching businesses with page={}, size={}", page, size);
        try {
            List<Business> businesses = businessService.getBusinessesByPage(page, size);
            logger.info("Successfully retrieved {} businesses", businesses.size());
            
            // 添加调试日志，输出每个商家的信息
            for (Business business : businesses) {
                logger.debug("Business: id={}, name={}, img={}", 
                    business.getBusinessId(), 
                    business.getBusinessName(),
                    business.getBusinessImg() != null ? 
                        (business.getBusinessImg().length() > 30 ? 
                            business.getBusinessImg().substring(0, 30) + "..." : 
                            business.getBusinessImg()) : 
                        "null");
            }
            
            return Result.success(businesses);
        } catch (Exception e) {
            logger.error("Error fetching businesses: {}", e.getMessage(), e);
            return Result.failure("获取商家列表失败: " + e.getMessage());
        }
    }
    
    @GetMapping(value = "/{businessId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBusinessById(@PathVariable int businessId) {
        logger.info("Fetching business with id={}", businessId);
        try {
            Business business = businessService.getBusinessById(businessId);
            if (business != null) {
                logger.info("Successfully retrieved business with id={}", businessId);
                return ResponseEntity.ok(Result.success(business));
            } else {
                logger.warn("Business with id={} not found", businessId);
                return ResponseEntity.status(404).body(Result.failure("商家不存在"));
            }
        } catch (Exception e) {
            logger.error("Error fetching business with id={}: {}", businessId, e.getMessage(), e);
            return ResponseEntity.status(500).body(Result.failure("获取商家详情失败: " + e.getMessage()));
        }
    }
    
    @GetMapping("/test/{businessId}")
    public Business testGetBusinessById(@PathVariable int businessId) {
        logger.info("Testing business retrieval with id={}", businessId);
        try {
            Business business = businessService.getBusinessById(businessId);
            if (business != null) {
                logger.info("Successfully retrieved test business with id={}", businessId);
                return business;
            } else {
                logger.warn("Test business with id={} not found", businessId);
                return null;
            }
        } catch (Exception e) {
            logger.error("Error in test fetching business with id={}: {}", businessId, e.getMessage(), e);
            throw e;
        }
    }
} 