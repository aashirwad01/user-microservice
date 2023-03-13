package com.useandsell.users.repository;

import com.useandsell.users.dto.common.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
