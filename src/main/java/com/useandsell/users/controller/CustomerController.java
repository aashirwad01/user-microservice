package com.useandsell.users.controller;

import com.useandsell.users.dto.Customer;
import com.useandsell.users.dto.common.Address;
import com.useandsell.users.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(path = "api/customersAll")
    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() throws Exception {
        try {
            return new ResponseEntity<>
                    (customerService.getCustomers(), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("Exception Occurred:", e);
        }
    }


    @RequestMapping(path = "api/addCustomer")
    @PostMapping
    public ResponseEntity<String> registerNewCustomer
            (@RequestBody Map<String, String> map)
            throws Exception {
        try {
            String emailVal = customerService
                    .addNewCustomerEmailwithAuthentication(
                            map.get("email"),
                            map.get("password"));

            if (!(emailVal.isBlank() && emailVal.isEmpty())) {
                Customer customer =
                        new Customer(
                                map.get("name"),
                                LocalDate.parse(map.get("dob")));
                customer.setEmail(emailVal);
                Address address =
                        new Address(
                                map.get("city"),
                                map.get("state"),
                                map.get("country"),
                                map.get("pincode"),
                                map.get("landmark"));
                customerService
                        .addNewCustomer(
                                customer,
                                address);
                return new ResponseEntity<>
                        ("User Registration Successfully as Customer.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>
                        ("Enter Correct Email.", HttpStatus.OK);
            }
        } catch (Exception e) {
            throw new Exception("Email already exists. ", e);
        }
    }

    @DeleteMapping(path = "api/{customerId}/delete")
    public ResponseEntity<String> deleteCustomer
            (@PathVariable("customerId")
             Long customerid) throws Exception {
        try {
            customerService.deleteCustomer(customerid);
            return new ResponseEntity<>
                    ("User Deleted Successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Exception Occurred: User does not exist ", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping(path = "api/{customerId}/update")
    public ResponseEntity<String> updateCustomer(
            @PathVariable("customerId") Long customerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDate dob)
            throws Exception {
        try {
            customerService.updateCustomer(customerId, name, dob);
            return new ResponseEntity<>
                    ("Details updated Successfully.", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Exception Occurred: Update unsuccessful ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "api/customerID/{email}")
    @GetMapping
    public Long getCustomerID(
            @PathVariable("email") String email
    ) throws Exception {
        try {
            return (customerService.getCustomerID(email));
        } catch (Exception e) {

            throw new Exception("Exception Occurred: ", e);
        }
    }


    @RequestMapping(path = "api/customer/{email}")
    @GetMapping
    public Optional<Customer> getCustomer(
            @PathVariable("email") String email
    ) throws Exception {
        try {
            return (customerService.getCustomer(email));
        } catch (Exception e) {

            throw new Exception("Exception Occurred: ", e);
        }
    }


}
