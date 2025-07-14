package yun.edu.rating.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yun.edu.rating.pojo.Rating;
import yun.edu.rating.service.RatingService;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {
    
    @Autowired
    private RatingService ratingService;
    
    @GetMapping("/order/{orderId}")
    public Rating getRatingByOrderId(@PathVariable Integer orderId) {
        return ratingService.getRatingByOrderId(orderId);
    }
    
    @GetMapping("/{ratingId}")
    public Rating getRatingById(@PathVariable Integer ratingId) {
        return ratingService.getRatingById(ratingId);
    }
    
    @PostMapping("/")
    public int saveRating(@RequestBody Rating rating) {
        return ratingService.saveRating(rating);
    }
    
    @PutMapping("/")
    public int updateRating(@RequestBody Rating rating) {
        return ratingService.updateRating(rating);
    }
    
    @GetMapping("/user/{userId}")
    public List<Rating> getRatingsByUserId(@PathVariable String userId) {
        return ratingService.getRatingsByUserId(userId);
    }
    
    @GetMapping("/business/{businessId}")
    public List<Rating> getRatingsByBusinessId(@PathVariable Integer businessId) {
        return ratingService.getRatingsByBusinessId(businessId);
    }
    
    @GetMapping("/food/{foodId}")
    public List<Rating> getRatingsByFoodId(@PathVariable Integer foodId) {
        return ratingService.getRatingsByFoodId(foodId);
    }
    
    @GetMapping("/status/{status}")
    public List<Rating> getRatingsByStatus(@PathVariable Integer status) {
        return ratingService.getRatingsByStatus(status);
    }
    
    @PutMapping("/approve/{ratingId}")
    public int approveRating(@PathVariable Integer ratingId) {
        return ratingService.approveRating(ratingId);
    }
    
    @PutMapping("/reject/{ratingId}")
    public int rejectRating(@PathVariable Integer ratingId) {
        return ratingService.rejectRating(ratingId);
    }
} 