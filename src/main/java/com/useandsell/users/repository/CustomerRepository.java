package com.useandsell.users.repository;

import com.useandsell.users.dto.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE c.email = ?1")
    Optional<Customer> findCustomerByEmail(String email);

    @Query("SELECT max( i.customerid ) FROM Customer i")
    Long findRecentId();

    @Query("UPDATE Customer c set c.name=:name, c.dob=:dob WHERE c.customerid=:id")
    void updateCustomer(@Param("id") Long id,
                        @Param("name") String name,
                        @Param("dob") LocalDate dob);

    @Query("UPDATE Customer c set c.name=:name WHERE c.customerid=:id")
    void updateCustomerName(@Param("id") Long id,
                            @Param("name") String name)
            ;

    @Query("UPDATE Customer c set c.dob=:dob WHERE c.customerid=:id")
    void updateCustomerDOB(@Param("id") Long id,
                           @Param("dob") LocalDate dob)
            ;

    @Query("SELECT c.customerid FROM Customer c WHERE c.email = ?1")
    Long findCustomerID(String email);
}
