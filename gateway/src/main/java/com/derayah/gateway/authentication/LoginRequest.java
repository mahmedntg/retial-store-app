package com.derayah.gateway.authentication;

import lombok.Data;

/**
 * LoginRequest.
 *
 * @author : Mo Sayed
 * @since : 5/24/2022
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
    
}
