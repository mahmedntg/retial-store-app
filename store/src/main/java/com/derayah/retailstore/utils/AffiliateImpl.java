package com.derayah.retailstore.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * AffiliateImpl.
 *
 * @author : Mo Sayed
 * @since : 5/25/2022
 */
public class AffiliateImpl implements UserInterface {
    private static final double AFFILIATE_DISCOUNT_PERCENTAGE = 0.10;

    @Override
    public BigDecimal apply(final Object o) {
        return BigDecimal.valueOf(AFFILIATE_DISCOUNT_PERCENTAGE).setScale(2, RoundingMode.HALF_EVEN);
    }
}
