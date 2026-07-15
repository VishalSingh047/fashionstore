package com.fashionstore.fashionstore.repository;

import com.fashionstore.fashionstore.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
