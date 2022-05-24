package com.derayah.gateway.authentication;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Role.
 *
 * @author : Mo Sayed
 * @since : 5/24/2022
 */
public enum Role {
    EMPLOYEE,
    AFFILIATE,
    CUSTOMER;
}
