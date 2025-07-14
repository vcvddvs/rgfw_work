package yun.edu.pojo;

import lombok.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class OrderDetailDTO {
    private String businessName;
    private String businessImg;
    private String foodImgs; // This will hold the comma-separated string from the database
    private String quantities; // This will hold the comma-separated string from the database
    private double orderTotal;
    private String orderDate;
    
    // Helper methods to convert the comma-separated strings to lists for the front-end
    public List<String> getFoodImgsList() {
        if (foodImgs == null || foodImgs.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(foodImgs.split(","));
    }
    
    public List<Integer> getQuantitiesList() {
        if (quantities == null || quantities.isEmpty()) {
            return new ArrayList<>();
        }
        List<Integer> result = new ArrayList<>();
        for (String quantity : quantities.split(",")) {
            try {
                result.add(Integer.parseInt(quantity.trim()));
            } catch (NumberFormatException e) {
                // Skip invalid numbers
            }
        }
        return result;
    }
}