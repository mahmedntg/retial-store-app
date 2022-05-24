package com.dreya.retailstore.controller;

import com.dreya.retailstore.request.DiscountRequest;
import com.dreya.retailstore.response.UserInfo;
import com.dreya.retailstore.service.BillService;
import com.dreya.retailstore.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * StoreController.
 *
 * @author : Mo Sayed
 * @since : 5/22/2022
 */
@RestController
@RequiredArgsConstructor
public class StoreController {

    private final BillService billService;
    private final UserService userService;

    @PostMapping("/pay")
    @ApiOperation(value = "Calculate discounted (net) amount of a bill, based on the user & amount")
    public ResponseEntity<BigDecimal> getDiscountedBill(@RequestBody @Valid DiscountRequest request){

        return new ResponseEntity<>(billService.applyDiscount(userService.getUserInfo(), request.getBill()), HttpStatus.OK);
    }

}
