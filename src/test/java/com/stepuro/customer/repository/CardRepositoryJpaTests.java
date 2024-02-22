package com.stepuro.customer.repository;

import com.stepuro.customer.model.Card;
import com.stepuro.customer.model.enums.CardStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.stepuro.customer.repository.Samples.CardSamples.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CardRepositoryJpaTests {
    @Autowired
    private CardRepositoryJpa cardRepositoryJpa;

    @Test
    public void CardRepository_Save_ReturnsSavedModel(){
        Card savedCard = cardRepositoryJpa.save(card1);

        assertNotNull(savedCard);
        assertEquals(card1.getAccountNumber(), savedCard.getAccountNumber());
        assertEquals(card1.getCreatedDate(), savedCard.getCreatedDate());
        assertEquals(card1.getUpdatedDate(), savedCard.getUpdatedDate());
        assertEquals(card1.getCardNumber(), savedCard.getCardNumber());
        assertEquals(card1.getExpiryDate(), savedCard.getExpiryDate());
        assertEquals(card1.getStatus(), savedCard.getStatus());
        assertEquals(card1.getBalance(), savedCard.getBalance());
    }

    @Test
    public void CardRepository_FindAll_ReturnsAllModels(){
        cardRepositoryJpa.save(card1);
        cardRepositoryJpa.save(card2);
        cardRepositoryJpa.save(card3);
        cardRepositoryJpa.save(card4);

        List<Card> cards = cardRepositoryJpa.findAll();

        assertNotNull(cards);
        assertEquals(4, cards.size());
    }

    @Test
    public void CardRepository_FindById_ReturnsModel(){
        Card savedCard = cardRepositoryJpa.save(card1);
        cardRepositoryJpa.save(card2);


        Card card = cardRepositoryJpa.findById(savedCard.getId()).get();

        assertNotNull(card);
        assertEquals(savedCard.getId(), card.getId());
        assertEquals(savedCard.getAccountNumber(), card.getAccountNumber());
        assertEquals(savedCard.getCreatedDate(), card.getCreatedDate());
        assertEquals(savedCard.getUpdatedDate(), card.getUpdatedDate());
        assertEquals(savedCard.getStatus(), card.getStatus());
        assertEquals(savedCard.getCardNumber(), card.getCardNumber());
        assertEquals(savedCard.getExpiryDate(), card.getExpiryDate());
        assertEquals(savedCard.getBalance(), card.getBalance());
    }

    @Test
    public void CardRepository_Update_ChangesModel(){
        Card savedCard = cardRepositoryJpa.save(card1);

        savedCard.setStatus(CardStatus.UNREACHABLE);
        savedCard.setAccountNumber("IE12BOFI90000112346666");
        savedCard.setCreatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(5)));
        savedCard.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(3)));
        savedCard.setCardNumber("5425233430109333");
        savedCard.setExpiryDate(Date.valueOf(LocalDate.now().plusYears(4)));
        savedCard.setBalance(new BigDecimal("233.50"));

        Card updatedCard = cardRepositoryJpa.save(savedCard);

        assertNotNull(updatedCard);
        assertEquals(savedCard.getId(), updatedCard.getId());
        assertEquals(savedCard.getStatus(), updatedCard.getStatus());
        assertEquals(savedCard.getAccountNumber(), updatedCard.getAccountNumber());
        assertEquals(savedCard.getCreatedDate(), updatedCard.getCreatedDate());
        assertEquals(savedCard.getUpdatedDate(), updatedCard.getUpdatedDate());
        assertEquals(savedCard.getCardNumber(), updatedCard.getCardNumber());
        assertEquals(savedCard.getExpiryDate(), updatedCard.getExpiryDate());
        assertEquals(savedCard.getBalance(), updatedCard.getBalance());
    }

    @Test
    public void CardRepository_Remove_RemovesModel(){
        Card savedCard = cardRepositoryJpa.save(card1);

        cardRepositoryJpa.deleteById(savedCard.getId());

        boolean existsById = cardRepositoryJpa.existsById(savedCard.getId());

        assertFalse(existsById);
    }
}
