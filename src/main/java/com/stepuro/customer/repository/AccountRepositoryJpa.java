package com.stepuro.customer.repository;

import com.stepuro.customer.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepositoryJpa extends JpaRepository<Account, UUID> {
}
