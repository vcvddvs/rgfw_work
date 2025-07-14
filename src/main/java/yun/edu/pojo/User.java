package yun.edu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
   private String userId;
   private String password;
   private String userName;
   private int userSex;
   private String userImg;
   private int delTag;

   public User(String userId, String password) {
      this.userId = userId;
      this.password = password;
   }
}
