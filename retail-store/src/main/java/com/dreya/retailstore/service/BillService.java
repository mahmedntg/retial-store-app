package com.dreya.retailstore.service;

import com.dreya.retailstore.dto.Bill;
import com.dreya.retailstore.dto.ItemType;
import com.dreya.retailstore.response.UserDetails;
import com.dreya.retailstore.utils.DiscountHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * BillService.
 *
 * @author : Mo Sayed
 * @since : 5/22/2022
 */
@Service
@RequiredArgsConstructor
public class BillService {
    private final UserService userService;

    public BigDecimal applyDiscount(final UserDetails user, final Bill bill) {
        DiscountHelper helper = new DiscountHelper();

        BigDecimal totalAmount = helper.calculateTotal(bill.getItems());
        BigDecimal groceryAmount = helper.calculateTotalPerType(bill.getItems(), ItemType.GROCERY);
        BigDecimal nonGroceryAmount = totalAmount.subtract(groceryAmount);
        BigDecimal userDiscount = helper.getUserDiscount(user);
        BigDecimal billsDiscount = helper.calculateBillsDiscount(totalAmount, new BigDecimal(100), new BigDecimal(5));
        if (nonGroceryAmount.compareTo(BigDecimal.ZERO) > 0) {
            nonGroceryAmount = helper.calculateDiscount(nonGroceryAmount, userDiscount);
        }

        BigDecimal finalAmount = (groceryAmount.add(nonGroceryAmount).subtract(billsDiscount));
        return finalAmount;
    }
}

