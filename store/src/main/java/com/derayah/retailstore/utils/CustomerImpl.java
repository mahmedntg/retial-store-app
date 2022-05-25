package com.derayah.retailstore.utils;

import com.derayah.retailstore.response.UserDetails;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

/**
 * CustomerImpl.
 *
 * @author : Mo Sayed
 * @since : 5/25/2022
 */
public class CustomerImpl implements UserInterface<Object> {
    private static final int YEARS_FOR_DISCOUNT = 2;
    private static final double CUSTOMER_DISCOUNT_PERCENTAGE = 0.05;

    @Override
    public BigDecimal apply(final Object o) {
        UserDetails user = (UserDetails) o;
        if (isCustomerSince(LocalDate.parse(user.getRegisterDate()), YEARS_FOR_DISCOUNT)) {
            return BigDecimal.valueOf(CUSTOMER_DISCOUNT_PERCENTAGE).setScale(2, RoundingMode.HALF_EVEN);
        }
        return BigDecimal.ZERO;
    }

    private boolean isCustomerSince(LocalDate registeredDate, long years) {
        Period period = Period.between(registeredDate, LocalDate.now());
        return period.getYears() >= years;
    }

}