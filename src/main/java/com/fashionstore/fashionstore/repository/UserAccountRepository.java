package com.fashionstore.fashionstore.repository;

import com.fashionstore.fashionstore.entity.UserAccount;
import com.fashionstore.fashionstore.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByEmail(String email);
    List<UserAccount> findByRole(Role role);
}
