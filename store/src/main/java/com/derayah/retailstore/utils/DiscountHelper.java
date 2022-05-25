package com.derayah.retailstore.utils;

import com.derayah.retailstore.dto.Item;
import com.derayah.retailstore.dto.ItemType;
import com.derayah.retailstore.dto.UserType;
import com.derayah.retailstore.response.UserAuthority;
import com.derayah.retailstore.response.UserDetails;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Set;

/**
 * DiscountHelper.
 *
 * @author : Mo Sayed
 * @since : 5/23/2022
 */
public class DiscountHelper {
    private static final int YEARS_FOR_DISCOUNT = 2;

    private static final double EMPLOYEE_DISCOUNT_PERCENTAGE = 0.30;
    private static final double AFFILIATE_DISCOUNT_PERCENTAGE = 0.10;
    private static final double CUSTOMER_DISCOUNT_PERCENTAGE = 0.05;


    public BigDecimal calculateTotal(List<Item> items) {
        return items.stream().map(i -> i.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalPerType(List<Item> items, ItemType type) {
        BigDecimal sum = new BigDecimal(0);

        if (type != null) {
            sum = items.stream().filter(i -> type.equals(i.getType())).map(i -> i.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        return sum;
    }

    public BigDecimal getUserDiscount(UserDetails user) {
        if (user == null) {
            throw new NullPointerException("User cannot be null");
        }

        BigDecimal discount = new BigDecimal(0);

        Set<UserAuthority> authorities = user.getAuthorities();
        UserType type = authorities.stream().map(authority -> authority.getAuthority()).findAny().get();

        switch (type) {
            case EMPLOYEE:
                discount = new BigDecimal(EMPLOYEE_DISCOUNT_PERCENTAGE).setScale(2, RoundingMode.HALF_EVEN);
                break;

            case AFFILIATE:
                discount = new BigDecimal(AFFILIATE_DISCOUNT_PERCENTAGE).setScale(2, RoundingMode.HALF_EVEN);
                break;

            case CUSTOMER:
                if (isCustomerSince(LocalDate.parse(user.getRegisterDate()), YEARS_FOR_DISCOUNT)) {
                    discount = new BigDecimal(CUSTOMER_DISCOUNT_PERCENTAGE).setScale(2, RoundingMode.HALF_EVEN);
                }
                break;

            default:
                break;
        }

        return discount;
    }

    public boolean isCustomerSince(LocalDate registeredDate, long years) {
        Period period = Period.between(registeredDate, LocalDate.now());
        return period.getYears() >= years;
    }

    public BigDecimal calculateBillsDiscount(BigDecimal totalAmount, BigDecimal amount, BigDecimal discountAmount) {
        int value = totalAmount.divide(amount).intValue();
        return discountAmount.multiply(new BigDecimal(value));
    }

    public BigDecimal calculateDiscount(BigDecimal amount, BigDecimal discount) {
        if (discount.doubleValue() > 1.0) {
            throw new IllegalArgumentException("Discount cannot be more than 100%");
        }

        BigDecimal x = amount.multiply(discount);
        return amount.subtract(x);
    }

}
