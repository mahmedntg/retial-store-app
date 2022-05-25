package com.derayah.retailstore;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.derayah.retailstore.controller.StoreController;

import static org.mockito.Mockito.doReturn;

import com.derayah.retailstore.response.UserDetails;
import com.derayah.retailstore.service.BillService;
import com.derayah.retailstore.service.UserService;
import com.derayah.retailstore.utils.DiscountHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

/**
 * StoreDiscountTest.
 *
 * @author : Mo Sayed
 * @since : 5/25/2022
 */
@WebMvcTest(value = StoreController.class)
@ExtendWith(SpringExtension.class)
public class StoreDiscountTest extends UnitTest {

    @SpyBean
    private DiscountHelper helper;

    @SpyBean
    private BillService billService;

    @SpyBean
    private UserService userService;

    private UserDetails affiliateUser;
    private UserDetails customerUser;
    private UserDetails employeeUser;

    @BeforeEach
    public void initUsers() throws IOException {
        affiliateUser = objectMapper.readValue(readResourceFile("affiliate_user.json"), UserDetails.class);
        employeeUser = objectMapper.readValue(readResourceFile("employee_user.json"), UserDetails.class);
        customerUser = objectMapper.readValue(readResourceFile("customer_user.json"), UserDetails.class);
    }

    @Test
    public void testCalculateTotal_GroceriesOnlyBillDiscount() throws Exception {

        doReturn(employeeUser).when(userService).getUserInfo();

        url("pay")
            .methodPost()
            .ignoreDefaultPaths()
            .bodyFile("grocery_bill_items.json")
            .expectedResponse("285.0")
            .assertSuccessfulResponse();
    }

    @Test
    public void testCalculateTotal_EmployeeDiscount() throws Exception {

        doReturn(employeeUser).when(userService).getUserInfo();

        url("pay")
            .methodPost()
            .ignoreDefaultPaths()
            .bodyFile("employee_bill_items.json")
            .expectedResponse("225.000")
            .assertSuccessfulResponse();
    }

    @Test
    public void testCalculateTotal_CustomerDiscount() throws Exception {

        doReturn(employeeUser).when(userService).getUserInfo();

        url("pay")
            .methodPost()
            .ignoreDefaultPaths()
            .bodyFile("employee_bill_items.json")
            .expectedResponse("225.000")
            .assertSuccessfulResponse();
    }

    @Test
    public void testCalculateTotal_AffiliateDiscount() throws Exception {

        doReturn(affiliateUser).when(userService).getUserInfo();

        url("pay")
            .methodPost()
            .ignoreDefaultPaths()
            .bodyFile("employee_bill_items.json")
            .expectedResponse("265.000")
            .assertSuccessfulResponse();
    }

}
