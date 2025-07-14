-- 创建rating表（如果不存在）
CREATE TABLE IF NOT EXISTS rating (
  ratingId INT AUTO_INCREMENT PRIMARY KEY,
  orderId INT NOT NULL,
  userId VARCHAR(50) NOT NULL,
  businessId INT,
  foodId INT,
  deliveryRating INT,
  foodRating INT,
  content TEXT,
  createTime DATETIME,
  status INT DEFAULT 0
); 