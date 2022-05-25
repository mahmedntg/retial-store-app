package com.derayah.retailstore.controller;

import com.derayah.retailstore.request.DiscountRequest;
import com.derayah.retailstore.service.BillService;
import com.derayah.retailstore.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.math.BigDecimal;

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
