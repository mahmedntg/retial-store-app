package com.derayah.gateway.authentication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * User.
 *
 * @author : Mo Sayed
 * @since : 5/24/2022
 */
@Document(collection = "users")
@Setter
@Getter
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private String name;
    private Integer active = 1;
    private boolean isLocked = false;
    private boolean isExpired = false;
    private boolean isEnabled = true;
    private String registerDate;
    private Set<Role> roles;
}
