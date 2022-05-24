package com.dreya.retailstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Item.
 *
 * @author : Mo Sayed
 * @since : 5/24/2022
 */
@Setter
@Getter
public class Item {
    private ItemType type;

    private BigDecimal price;
}
