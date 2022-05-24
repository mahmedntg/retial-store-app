package com.dreya.retailstore.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * UserInfo.
 *
 * @author : Mo Sayed
 * @since : 5/24/2022
 */
@Setter
@Getter
public class UserInfo {
    @JsonProperty("userDetails")
    private UserDetails userDetails;
}
