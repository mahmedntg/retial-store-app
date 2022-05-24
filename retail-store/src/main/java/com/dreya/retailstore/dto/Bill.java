package com.dreya.retailstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Bill.
 *
 * @author : Mo Sayed
 * @since : 5/24/2022
 */
@Setter
@Getter
public class Bill {
    private List<Item> items;
}
