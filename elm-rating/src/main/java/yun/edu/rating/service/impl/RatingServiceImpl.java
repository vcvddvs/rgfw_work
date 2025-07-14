package yun.edu.rating.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yun.edu.rating.mapper.RatingMapper;
import yun.edu.rating.pojo.Rating;
import yun.edu.rating.service.RatingService;

import java.util.Date;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    
    @Autowired
    private RatingMapper ratingMapper;
    
    @Override
    public Rating getRatingByOrderId(Integer orderId) {
        return ratingMapper.getRatingByOrderId(orderId);
    }
    
    @Override
    public Rating getRatingById(Integer ratingId) {
        return ratingMapper.getRatingById(ratingId);
    }
    
    @Override
    public int saveRating(Rating rating) {
        rating.setCreateTime(new Date());
        rating.setStatus(0); // 默认待审核
        return ratingMapper.saveRating(rating);
    }
    
    @Override
    public int updateRating(Rating rating) {
        return ratingMapper.updateRating(rating);
    }
    
    @Override
    public List<Rating> getRatingsByUserId(String userId) {
        return ratingMapper.getRatingsByUserId(userId);
    }
    
    @Override
    public List<Rating> getRatingsByBusinessId(Integer businessId) {
        return ratingMapper.getRatingsByBusinessId(businessId);
    }
    
    @Override
    public List<Rating> getRatingsByFoodId(Integer foodId) {
        return ratingMapper.getRatingsByFoodId(foodId);
    }
    
    @Override
    public List<Rating> getRatingsByStatus(Integer status) {
        return ratingMapper.getRatingsByStatus(status);
    }
    
    @Override
    public int approveRating(Integer ratingId) {
        Rating rating = ratingMapper.getRatingById(ratingId);
        if (rating != null) {
            rating.setStatus(1); // 已通过
            return ratingMapper.updateRating(rating);
        }
        return 0;
    }
    
    @Override
    public int rejectRating(Integer ratingId) {
        Rating rating = ratingMapper.getRatingById(ratingId);
        if (rating != null) {
            rating.setStatus(2); // 已拒绝
            return ratingMapper.updateRating(rating);
        }
        return 0;
    }
} 