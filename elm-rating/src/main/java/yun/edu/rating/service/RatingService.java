package yun.edu.rating.service;

import yun.edu.rating.pojo.Rating;

import java.util.List;

public interface RatingService {
    
    Rating getRatingByOrderId(Integer orderId);
    
    Rating getRatingById(Integer ratingId);
    
    int saveRating(Rating rating);
    
    int updateRating(Rating rating);
    
    List<Rating> getRatingsByUserId(String userId);
    
    List<Rating> getRatingsByBusinessId(Integer businessId);
    
    List<Rating> getRatingsByFoodId(Integer foodId);
    
    List<Rating> getRatingsByStatus(Integer status);
    
    int approveRating(Integer ratingId);
    
    int rejectRating(Integer ratingId);
} 