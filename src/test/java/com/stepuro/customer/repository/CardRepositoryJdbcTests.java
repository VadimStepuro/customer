package com.stepuro.customer.repository;

import com.stepuro.customer.model.Card;
import com.stepuro.customer.model.enums.CardStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.stepuro.customer.repository.Samples.CardSamples.card1;
import static com.stepuro.customer.repository.Samples.CardSamples.card2;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CardRepositoryJdbcTests {
    @Autowired
    private CardRepositoryJdbc cardRepositoryJdbc;

    @Test
    public void CardRepositoryJdbc_Save_SavesModel(){
        UUID result = cardRepositoryJdbc.save(card1);

        Card savedCard = cardRepositoryJdbc.findById(result);

        assertNotNull(savedCard);
        assertEquals(card1.getAccountNumber(), savedCard.getAccountNumber());
        assertEquals(card1.getCreatedDate().getTime(), savedCard.getCreatedDate().getTime());
        assertEquals(card1.getUpdatedDate().getTime(), savedCard.getUpdatedDate().getTime());
        assertEquals(card1.getCardNumber(), savedCard.getCardNumber());
        assertEquals(card1.getExpiryDate(), savedCard.getExpiryDate());
        assertEquals(card1.getStatus(), savedCard.getStatus());
    }

    @Test
    public void CardRepositoryJdbc_FindById_ReturnsModel(){
        UUID id = cardRepositoryJdbc.save(card1);
        cardRepositoryJdbc.save(card2);

        Card card = cardRepositoryJdbc.findById(id);

        assertNotNull(card);
        assertEquals(id, card.getId());
        assertEquals(card1.getAccountNumber(), card.getAccountNumber());
        assertEquals(card1.getCreatedDate().getTime(), card.getCreatedDate().getTime());
        assertEquals(card1.getUpdatedDate().getTime(), card.getUpdatedDate().getTime());
        assertEquals(card1.getStatus(), card.getStatus());
        assertEquals(card1.getCardNumber(), card.getCardNumber());
        assertEquals(card1.getExpiryDate(), card.getExpiryDate());
    }

    @Test
    public void CardRepositoryJdbc_Edit_ChangesModel(){
        UUID id = cardRepositoryJdbc.save(card1);

        Card savedCard = cardRepositoryJdbc.findById(id);

        savedCard.setStatus(CardStatus.UNREACHABLE);
        savedCard.setAccountNumber("IE12BOFI90000112346666");
        savedCard.setCreatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(5)));
        savedCard.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(3)));
        savedCard.setCardNumber("5425233430109333");
        savedCard.setExpiryDate(Date.valueOf(LocalDate.now().plusYears(4)));

        cardRepositoryJdbc.edit(savedCard);

        Card updatedCard = cardRepositoryJdbc.findById(id);



        assertNotNull(updatedCard);
        assertEquals(savedCard.getId(), updatedCard.getId());
        assertEquals(savedCard.getStatus(), updatedCard.getStatus());
        assertEquals(savedCard.getAccountNumber(), updatedCard.getAccountNumber());
        assertEquals(savedCard.getCreatedDate().getTime(), updatedCard.getCreatedDate().getTime());
        assertEquals(savedCard.getUpdatedDate().getTime(), updatedCard.getUpdatedDate().getTime());
        assertEquals(savedCard.getCardNumber(), updatedCard.getCardNumber());
        assertEquals(savedCard.getExpiryDate(), updatedCard.getExpiryDate());
    }

    @Test
    public void CardRepositoryJdbc_Remove_RemovesModel(){
        UUID savedId = cardRepositoryJdbc.save(card1);

        cardRepositoryJdbc.deleteById(savedId);

        Card foundCard = cardRepositoryJdbc.findById(savedId);

        assertNull(foundCard);
    }
}
