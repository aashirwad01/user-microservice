package com.useandsell.users.service;

import com.useandsell.users.dto.Seller;
import com.useandsell.users.dto.common.Address;
import com.useandsell.users.repository.AddressRepository;
import com.useandsell.users.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class SellerService {
    private static final Logger LOGGER =
            Logger.getLogger(SellerService.class.getName());
    private final SellerRepository sellerRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public SellerService(SellerRepository sellerRepository, AddressRepository addressRepository) {
        this.sellerRepository = sellerRepository;
        this.addressRepository = addressRepository;
    }

    public List<Seller> getSellers() throws Exception {
        try {
            return sellerRepository.findAll();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred:", e);
        }
    }

    public Optional<Seller> getSeller(String email) throws Exception {
        try {
            return sellerRepository.findSellerByEmail(email);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }

    public String addNewSellerEmailwithAuthentication(String email, String password) throws Exception {

        try {
            String uri = "http://localhost:8084/api/seller/addUser";
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("password", password);
            String emailVal = restTemplate.postForObject(uri, params, String.class);
            return emailVal;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Already registered as customer. Use different email ", e.getMessage());
            throw new Exception("Already registered as customer. Use different email ", e);

        }

    }

    public void addNewSeller(Seller seller, Address address) throws Exception {
        try {
            Optional<Seller> sellerOptional = sellerRepository.findSellerByEmail(seller.getEmail());
            if (sellerOptional.isPresent()) {
                throw new IllegalArgumentException("Email Already Used.");
            }

            sellerRepository.save(seller);
            address.setSeller(seller);
            addressRepository.save(address);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }
}
