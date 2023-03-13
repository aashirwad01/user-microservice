package com.useandsell.users.dto.common;

import com.useandsell.users.dto.Customer;
import com.useandsell.users.dto.Seller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table
public class Address {
    @Id
    @SequenceGenerator(
            name = "address_sequence",
            sequenceName = "address_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "address_sequence"
    )
    @Column(nullable = false)
    public Long addressid;
    @Column(nullable = false)
    public String city;
    @Column(nullable = false)
    public String state;
    @Column(nullable = false)
    public String country;
    @Column(nullable = false)
    public String pincode;
    @Column(nullable = false)
    public String landmark;

    @OneToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "customerid")
    private Customer customer;

    @OneToOne(targetEntity = Seller.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "sellerid")
    private Seller seller;

    public Address() {
    }

    public Address(String city,
                   String state,
                   String country,
                   String pincode,
                   String landmark) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
        this.landmark = landmark;
    }


    public Long getAddressid() {
        return addressid;
    }

    public void setAddressid(Long addressid) {
        this.addressid = addressid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller sellerId) {
        this.seller = seller;
    }
}

