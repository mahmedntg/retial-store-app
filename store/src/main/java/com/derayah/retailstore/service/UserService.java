package com.derayah.retailstore.service;

import com.derayah.retailstore.exception.InternalException;
import com.derayah.retailstore.response.UserDetails;
import com.derayah.retailstore.response.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import java.net.URI;
import java.util.Optional;

/**
 * UserService.
 *
 * @author : Mo Sayed
 * @since : 5/22/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final HttpServletRequest servletRequest;

    @Value("${user.baseurl}")
    private String userUri;

    /**
     * get user info.
     *
     * @return UserDetails
     */
    @SneakyThrows
    public UserDetails getUserInfo() {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI(userUri);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", servletRequest.getHeader("Authorization"));

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<UserInfo> user = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, UserInfo.class);

        if (user.getStatusCode().equals(HttpStatus.OK)) {
            return Optional.ofNullable(user.getBody()).map(UserInfo::getUserDetails).orElseThrow(() -> {
                throw new InternalException("no user found");
            });
        }
        throw new InternalException("no user found");
    }
}
