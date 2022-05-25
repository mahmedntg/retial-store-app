package com.derayah.retailstore.utils;

import com.derayah.retailstore.dto.Item;
import com.derayah.retailstore.dto.ItemType;
import com.derayah.retailstore.dto.UserType;
import com.derayah.retailstore.exception.InternalException;
import com.derayah.retailstore.response.UserAuthority;
import com.derayah.retailstore.response.UserDetails;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * DiscountHelper.
 *
 * @author : Mo Sayed
 * @since : 5/23/2022
 */
@Component
public class DiscountHelper {

    static final UserContext context = new UserContext();

    static {
        context.register(UserType.CUSTOMER, new CustomerImpl());
        context.register(UserType.EMPLOYEE, new EmployeeImpl());
        context.register(UserType.AFFILIATE, new AffiliateImpl());
    }

    public BigDecimal calculateTotal(List<Item> items) {
        return items.stream().map(Item::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalPerType(List<Item> items, ItemType type) {
        BigDecimal sum = new BigDecimal(0);

        if (type != null) {
            sum = items.stream().filter(i -> type.equals(i.getType())).map(Item::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        return sum;
    }

    public BigDecimal getUserDiscount(UserDetails user) {
        if (user == null) {
            throw new NullPointerException("User cannot be null");
        }
        Set<UserAuthority> authorities = user.getAuthorities();
        UserType type = authorities.stream().map(UserAuthority::getAuthority).findAny().orElseThrow(InternalException::new);

        return (BigDecimal) context.call(type, user);
    }

    public BigDecimal calculateBillsDiscount(BigDecimal totalAmount, BigDecimal amount, BigDecimal discountAmount) {
        int value = totalAmount.divide(amount).intValue();
        return discountAmount.multiply(BigDecimal.valueOf(value));
    }

    public BigDecimal calculateDiscount(BigDecimal amount, BigDecimal discount) {
        if (discount.doubleValue() > 1.0) {
            throw new IllegalArgumentException("Discount cannot be more than 100%");
        }

        BigDecimal x = amount.multiply(discount);
        return amount.subtract(x);
    }

}
