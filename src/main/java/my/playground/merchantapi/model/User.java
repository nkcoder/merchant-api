package my.playground.merchantapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("USERS")
public class User {
    @Id
    private Long userID;
    private final String userName;
    private final String email;
    private final String password;
    private final String userType;
    private final LocalDateTime dateRegistered;

    public User(String userName, String email, String password, String userType, LocalDateTime dateRegistered) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.dateRegistered = dateRegistered;
    }

    public String getEmail() {
        return email;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public LocalDateTime getDateRegistered() {
        return dateRegistered;
    }
}
