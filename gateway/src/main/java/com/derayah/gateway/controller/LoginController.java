package com.derayah.gateway.controller;

import com.derayah.gateway.authentication.AuthResponse;
import com.derayah.gateway.authentication.LoginRequest;
import com.derayah.gateway.authentication.MongoUserDetails;
import com.derayah.gateway.repository.JwtTokenRepository;
import com.derayah.gateway.repository.UserRepository;
import com.derayah.gateway.security.JwtUtils;
import com.derayah.gateway.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * LoginController.
 *
 * @author : Mo Sayed
 * @since : 5/24/2022
 */
@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenRepository jwtTokenRepository;
    private final UserService userDetailsService;

    @CrossOrigin("*")
    @PostMapping("/auth/signin")
    @ResponseBody
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {

        Authentication authentication =
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @GetMapping("/auth/token")
    @CrossOrigin("*")
    @ResponseBody
    public ResponseEntity<AuthResponse> getTokenInfo(HttpServletRequest request) {
        String jwt = parseJwt(request);
        if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
            String username = jwtUtils.getUserNameFromJwtToken(jwt);
            MongoUserDetails userDetails = userDetailsService.getUserDetails(username);
            return new ResponseEntity<>(new AuthResponse(userDetails), HttpStatus.OK);
        }
        return null;
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}
