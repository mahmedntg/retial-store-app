package com.derayah.retailstore.request;

import com.derayah.retailstore.dto.Bill;
import lombok.Getter;
import lombok.Setter;

/**
 * DiscountRequest.
 *
 * @author : Mo Sayed
 * @since : 5/23/2022
 */
@Setter
@Getter
public class DiscountRequest {
    private Bill bill;
}
