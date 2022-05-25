package com.derayah.retailstore.utils;

import com.derayah.retailstore.response.UserDetails;

import java.math.BigDecimal;

public interface UserInterface<T> {
    public BigDecimal apply(T t);
}
