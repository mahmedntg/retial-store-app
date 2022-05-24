package com.derayah.gateway;

import com.derayah.gateway.authentication.Role;
import com.derayah.gateway.authentication.User;
import com.derayah.gateway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

/**
 * UserConfig.
 *
 * @author : Mo Sayed
 * @since : 5/24/2022
 */
@Component
@RequiredArgsConstructor
public class UserConfig {

    private final UserRepository userRepository;

    @PostConstruct
    public void init() {

        userRepository.deleteAll();

        User employee = new User();
        employee.setUsername("employee");
        employee.setPassword(new BCryptPasswordEncoder().encode("test1"));
        employee.setRoles(Collections.singleton(Role.EMPLOYEE));
        employee.setRegisterDate("2022-01-01");
        userRepository.save(employee);

        User affiliate = new User();
        affiliate.setUsername("affiliate");
        affiliate.setPassword(new BCryptPasswordEncoder().encode("test1"));
        affiliate.setRoles(Collections.singleton(Role.AFFILIATE));
        affiliate.setRegisterDate("2022-01-01");
        userRepository.save(affiliate);

        User customer = new User();
        customer.setUsername("customer");
        customer.setPassword(new BCryptPasswordEncoder().encode("test1"));
        customer.setRoles(Collections.singleton(Role.CUSTOMER));
        customer.setRegisterDate("2000-01-01");
        userRepository.save(customer);
    }
}
