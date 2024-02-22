package com.stepuro.customer.repository;

import com.stepuro.customer.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CardRepositoryJpa extends JpaRepository<Card, UUID> {
    Optional<Card> findByCardNumber(String cardNumber);

    boolean existsByCardNumber(String cardNumber);
}
