package com.dreya.retailstore.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * BillAmountDto.
 *
 * @author : Mo Sayed
 * @since : 5/22/2022
 */
@Setter
@Getter
@Builder
public class BillAmountDto {
    private BigDecimal discountedBill;
    private BigDecimal bill;
}
