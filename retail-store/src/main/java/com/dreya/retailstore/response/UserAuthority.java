package com.dreya.retailstore.response;

import com.dreya.retailstore.dto.UserType;
import lombok.Getter;
import lombok.Setter;

/**
 * UserAuthority.
 *
 * @author : Mo Sayed
 * @since : 5/24/2022
 */
@Setter
@Getter
public class UserAuthority {
    private UserType authority;
}
