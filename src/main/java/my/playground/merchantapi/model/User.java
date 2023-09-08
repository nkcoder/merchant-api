package my.playground.merchantapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("USERS")
@RequiredArgsConstructor
@Getter
public class User {
    @Id
    @Setter
    private Long userId;
    private final String userName;
    private final String email;
    private final String password;
    private final String userType;
    private final LocalDateTime dateRegistered;
}
