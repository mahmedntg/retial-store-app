package com.derayah.retailstore.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * UserDetails.
 *
 * @author : Mo Sayed
 * @since : 5/24/2022
 */
@Setter
@Getter
public class UserDetails {

    @JsonProperty("username")
    private String username;

    @JsonProperty("authorities")
    private Set<UserAuthority> authorities;

    private String registerDate;
}
