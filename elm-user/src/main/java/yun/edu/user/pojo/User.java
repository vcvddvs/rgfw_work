package yun.edu.user.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String userId;
    private String password;
    private String userName;
    private Integer userSex;
    private String userImg;
    private Integer delTag;

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}