package com.useandsell.users.service;


import com.useandsell.users.dto.Customer;
import com.useandsell.users.dto.common.Address;
import com.useandsell.users.repository.AddressRepository;
import com.useandsell.users.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CustomerService {
    private static final Logger LOGGER =
            Logger.getLogger(CustomerService.class.getName());
    private static final String uriLogin = "http://localhost:8084/api/customer/addUser";
    private static final String uri = "http://localhost:8084/api/isLoggedIn";
    private static final String isSellerUri = "http://localhost:8084/api/isSeller";
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public CustomerService(
            CustomerRepository customerRepository,
            AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    public List<Customer> getCustomers() throws Exception {
        try {
            return customerRepository.findAll();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }

    public boolean addNewCustomerwithAuthentication(String email) throws Exception {
        try {

            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> params = new HashMap<>();
            params.put("email", email);

            Boolean loginVal = restTemplate.postForObject(uri, params, Boolean.class);
            Boolean sellerVal = restTemplate.postForObject(isSellerUri, params, Boolean.class);

            return loginVal && (!(sellerVal));

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);

        }
    }

    public String addNewCustomerEmailwithAuthentication(String email, String password) throws Exception {
        try {

            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> params = new HashMap<>();
            params.put("password", password);
            params.put("email", email);
            String emailVal = restTemplate
                    .postForObject(uriLogin, params, String.class);
            return emailVal;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            return null;
        }

    }

    public void addNewCustomer(Customer customer, Address address)
            throws Exception {
        try {
            Optional<Customer> customerOptional = customerRepository
                    .findCustomerByEmail(customer.getEmail());
            if (customerOptional.isPresent()) {
                throw new Exception
                        ("Email Already Used.");
            }
            customerRepository.save(customer);
            address.setCustomer(customer);
            addressRepository.save(address);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }

    public void deleteCustomer(Long customerId) throws Exception {
        try {
            boolean customerExists = customerRepository
                    .existsById(customerId);
            if (!customerExists) {
                throw new Exception(
                        "Customer with ID "
                                + customerId +
                                " does not exists.");

            }
            addressRepository.deleteById(customerId);
            customerRepository.deleteById(customerId);


        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception(
                    "Customer with ID "
                            + customerId +
                            " does not exists.");
        }
    }

    @Transactional
    public void updateCustomer(Long customerId,
                               String name,
                               LocalDate dob) throws Exception {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new Exception
                        ("Customer with id"
                                + customerId +
                                "does not exist"
                        ));


        if (name != null && name.length() > 0 &&
                !Objects.equals(customer.getName(), name)) {

            customer.setName(name);
        } else {
            throw new Exception("Enter updated name value");
        }
        if (dob != null &&
                !Objects.equals(customer.getDob(), dob)) {
            customer.setDob(dob);
        } else {
            throw new Exception("Enter updated dob value");
        }


    }

    public Long getCustomerID(String email) throws Exception {
        try {
            return customerRepository.findCustomerID(email);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }

    public Optional<Customer> getCustomer(String email) throws Exception {
        try {
            return customerRepository.findCustomerByEmail(email);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }
}

