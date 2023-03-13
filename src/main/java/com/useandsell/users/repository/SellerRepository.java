package com.useandsell.users.repository;

import com.useandsell.users.dto.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("SELECT s FROM Seller s WHERE s.email = ?1")
    Optional<Seller> findSellerByEmail(String email);

    @Query("SELECT c.sellerid FROM Seller c WHERE c.email = ?1")
    Long findSellerID(String email);
}
