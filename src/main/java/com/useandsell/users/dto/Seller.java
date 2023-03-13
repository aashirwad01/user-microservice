package com.useandsell.users.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table
public class Seller {
    @Id
    @SequenceGenerator(
            name = "seller_sequence",
            sequenceName = "seller_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seller_sequence"
    )
    private Long sellerid;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;

    public Seller() {
    }

    public Seller(Long sellerid,
                  String name,
                  String email) {
        this.sellerid = sellerid;
        this.name = name;
        this.email = email;
    }

    public Seller(
            String name,
            String email) {

        this.name = name;
        this.email = email;
    }

    public Seller(
            String name
    ) {

        this.name = name;

    }

    public Long getSellerid() {
        return sellerid;
    }

    public void setSellerid(Long sellerid) {
        this.sellerid = sellerid;
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

    @Override
    public String toString() {
        return "Seller{" +
                "sellerid=" + sellerid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
