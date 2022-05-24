package com.derayah.gateway.security;

import com.derayah.gateway.authentication.MongoUserDetails;
import com.derayah.gateway.authentication.User;
import com.derayah.gateway.exception.CustomException;
import com.derayah.gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * UserService.
 *
 * @author : Mo Sayed
 * @since : 5/24/2022
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName);
        if (user == null || CollectionUtils.isEmpty(user.getRoles())) {
            throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
        }
        return new MongoUserDetails(user);
    }

    public MongoUserDetails getUserDetails(String userName) {
        User user = userRepository.findByUsername(userName);
        return new MongoUserDetails(user);
    }


}
