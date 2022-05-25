package com.derayah.retailstore.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * EmployeeImpl.
 *
 * @author : Mo Sayed
 * @since : 5/25/2022
 */

public class EmployeeImpl implements UserInterface<Object> {
    private static final double EMPLOYEE_DISCOUNT_PERCENTAGE = 0.30;

    @Override
    public BigDecimal apply(final Object o) {
        return BigDecimal.valueOf(EMPLOYEE_DISCOUNT_PERCENTAGE).setScale(2, RoundingMode.HALF_EVEN);
    }
}
