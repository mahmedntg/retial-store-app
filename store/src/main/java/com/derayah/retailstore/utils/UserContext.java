package com.derayah.retailstore.utils;

import com.derayah.retailstore.dto.UserType;

import java.util.EnumMap;

/**
 * UserContext.
 *
 * @author : Mo Sayed
 * @since : 5/25/2022
 */
public class UserContext {

    EnumMap<UserType, UserInterface<Object>> context = new EnumMap<>(UserType.class);

    public void register(UserType name, UserInterface<Object> function) {
        context.put(name, function);
    }

    public Object call(UserType userType, Object o) {
        return context.get(userType).apply(o);
    }

    public UserInterface<Object> get(UserType type) {
        return context.get(type);
    }

}
