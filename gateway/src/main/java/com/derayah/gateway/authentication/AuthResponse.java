package com.derayah.gateway.authentication;

import lombok.Getter;
import lombok.Setter;

/**
 * AuthResponse.
 *
 * @author : Mo Sayed
 * @since : 5/24/2022
 */
@Setter
@Getter
public class AuthResponse {
    private String accessToken;
    private MongoUserDetails userDetails;

    public AuthResponse(final MongoUserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

}
