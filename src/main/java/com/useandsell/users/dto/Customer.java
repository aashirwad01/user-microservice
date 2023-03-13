package com.useandsell.users.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table
public class Customer {
    @Id
    @SequenceGenerator(
            name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence"

    )
    private Long customerid;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private LocalDate dob;
    @Transient
    private Integer age;

    public Customer() {
    }

    public Customer(Long customerid,
                    String name,
                    String email,
                    LocalDate dob
    ) {
        this.customerid = customerid;
        this.name = name;
        this.email = email;
        this.dob = dob;

    }


    public Customer(String name,
                    String email,
                    LocalDate dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;

    }

    public Customer(String name,
                    LocalDate dob) {
        this.name = name;

        this.dob = dob;

    }


    public Long getCustomerId() {
        return customerid;
    }

    public void setId(Long customerid) {
        this.customerid = customerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getAge() {
        return Period.between(this.dob,
                        LocalDate.now())
                .getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + customerid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", age=" + age +
                '}';
    }
}
