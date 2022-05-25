package com.derayah.retailstore.response;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * BillDto.
 *
 * @author : Mo Sayed
 * @since : 5/22/2022
 */
@Setter
@Getter
public class BillDto {

    @Min(0)
    @NotNull
    private BigDecimal bill;

    private boolean includeGroceries;
}
