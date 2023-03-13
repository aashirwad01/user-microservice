package com.useandsell.users.controller;


import com.useandsell.users.dto.Seller;
import com.useandsell.users.dto.common.Address;
import com.useandsell.users.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class SellerController {
    private final SellerService sellerService;

    @Autowired
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @RequestMapping(path = "api/sellersAll")
    @GetMapping
    public ResponseEntity<List<Seller>> getSellers() throws Exception {
        try {
            return new ResponseEntity<>
                    (sellerService.getSellers(), HttpStatus.OK);

        } catch (Exception e) {
            throw new Exception("Exception Occurred:", e);
        }
    }

    @RequestMapping(path = "api/seller/{email}")
    @GetMapping
    public Optional<Seller> getSellerByEmail(
            @PathVariable("email") String email
    ) throws Exception {
        try {
            return (sellerService.getSeller(email));
        } catch (Exception e) {

            throw new Exception("Exception Occurred: ", e);
        }
    }

    @RequestMapping(path = "api/addSeller")
    @PostMapping
    public ResponseEntity<String> registerNewSeller(
            @RequestBody Map<String, String> map)
            throws Exception {
        try {
            String emailVal = sellerService
                    .addNewSellerEmailwithAuthentication(
                            map.get("email"),
                            map.get("password"));

            if (!(emailVal.isBlank())) {
                Seller seller = new Seller(map.get("name"));
                seller.setEmail(emailVal);

                Address address = new Address(
                        map.get("city"),
                        map.get("state"),
                        map.get("country"),
                        map.get("pincode"),
                        map.get("landmark"));
                sellerService.addNewSeller(seller, address);

                return new ResponseEntity<>
                        ("User Registration Successfully as Seller.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>
                        ("Enter Correct Email.", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Already Registered as user. Enter new email ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
