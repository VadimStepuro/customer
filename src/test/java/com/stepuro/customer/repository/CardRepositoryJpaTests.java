package com.stepuro.customer.repository;

import com.stepuro.customer.model.Card;
import com.stepuro.customer.model.status.CardStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CardRepositoryJpaTests {
    @Autowired
    private CardRepositoryJpa cardRepositoryJpa;

    @Test
    public void CardRepository_Save_ReturnsSavedModel(){
        //Arrange
        Card card = Card.builder()
                .accountNumber("IE12BOFI90000112345678")
                .cardNumber("5425233430109903")
                .status(CardStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
                .build();

        //Act
        Card savedCard = cardRepositoryJpa.save(card);

        //Assert
        assertNotNull(savedCard);
        assertEquals(card.getAccountNumber(), savedCard.getAccountNumber());
        assertEquals(card.getCreatedDate(), savedCard.getCreatedDate());
        assertEquals(card.getUpdatedDate(), savedCard.getUpdatedDate());
        assertEquals(card.getCardNumber(), savedCard.getCardNumber());
        assertEquals(card.getExpiryDate(), savedCard.getExpiryDate());
        assertEquals(card.getStatus(), savedCard.getStatus());
    }

    @Test
    public void CardRepository_FindAll_ReturnsAllModels(){
        Card card1 = Card.builder()
                .accountNumber("IE12BOFI90000112345678")
                .cardNumber("5425233430109903")
                .status(CardStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
                .build();
        Card card2 = Card.builder()
                .accountNumber("IE12BOFI90000112345678")
                .cardNumber("5425233430109903")
                .status(CardStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
                .build();
        Card card3 = Card.builder()
                .accountNumber("IE12BOFI90000112345678")
                .cardNumber("5425233430109903")
                .status(CardStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
                .build();
        Card card4 = Card.builder()
                .accountNumber("IE12BOFI90000112345678")
                .cardNumber("5425233430109903")
                .status(CardStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
                .build();

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
        Card card1 = Card.builder()
                .accountNumber("IE12BOFI90000112345555")
                .cardNumber("5425233430109903")
                .status(CardStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
                .build();
        Card card2 = Card.builder()
                .accountNumber("IE12BOFI90000112345666")
                .cardNumber("5425233430109333")
                .status(CardStatus.UNREACHABLE)
                .createdDate(Date.valueOf(LocalDate.now().minusMonths(5)))
                .updatedDate(Date.valueOf(LocalDate.now().minusMonths(3)))
                .expiryDate(Date.valueOf(LocalDate.now().plusYears(4)))
                .build();

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
    }

    @Test
    public void CardRepository_Update_ChangesModel(){
        Card card1 = Card.builder()
                .accountNumber("IE12BOFI90000112345555")
                .cardNumber("5425233430109903")
                .status(CardStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
                .build();


        Card savedCard = cardRepositoryJpa.save(card1);

        savedCard.setStatus(CardStatus.UNREACHABLE);
        savedCard.setAccountNumber("IE12BOFI90000112346666");
        savedCard.setCreatedDate(Date.valueOf(LocalDate.now().minusMonths(5)));
        savedCard.setUpdatedDate(Date.valueOf(LocalDate.now().minusMonths(3)));
        savedCard.setCardNumber("5425233430109333");
        savedCard.setExpiryDate(Date.valueOf(LocalDate.now().plusYears(4)));

        Card updatedCard = cardRepositoryJpa.save(savedCard);

        assertNotNull(updatedCard);
        assertEquals(savedCard.getId(), updatedCard.getId());
        assertEquals(savedCard.getStatus(), updatedCard.getStatus());
        assertEquals(savedCard.getAccountNumber(), updatedCard.getAccountNumber());
        assertEquals(savedCard.getCreatedDate(), updatedCard.getCreatedDate());
        assertEquals(savedCard.getUpdatedDate(), updatedCard.getUpdatedDate());
        assertEquals(savedCard.getCardNumber(), updatedCard.getCardNumber());
        assertEquals(savedCard.getExpiryDate(), updatedCard.getExpiryDate());
    }

    @Test
    public void CardRepository_Remove_RemovesModel(){
        Card card1 = Card.builder()
                .accountNumber("IE12BOFI90000112345555")
                .cardNumber("5425233430109903")
                .status(CardStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
                .build();

        Card savedCard = cardRepositoryJpa.save(card1);

        cardRepositoryJpa.deleteById(savedCard.getId());

        boolean existsById = cardRepositoryJpa.existsById(savedCard.getId());

        assertFalse(existsById);
    }
}
