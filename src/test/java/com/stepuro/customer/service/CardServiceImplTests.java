package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.CardDto;
import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import com.stepuro.customer.model.Card;
import com.stepuro.customer.model.enums.CardStatus;
import com.stepuro.customer.repository.CardRepositoryJpa;
import com.stepuro.customer.service.impl.CardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.stepuro.customer.service.samples.CardSamples.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTests {
    @Mock
    private CardRepositoryJpa cardRepositoryJpa;

    @InjectMocks
    private CardServiceImpl cardServiceImpl;

    @Test
    void CardService_FindAll_ReturnsAllModels(){
        when(cardRepositoryJpa.findAll()).thenReturn(cardList);

        //Act
        List<CardDto> allCards = cardServiceImpl.findAll();

        //Assert
        assertNotNull(allCards);
        assertEquals(4, allCards.size());
    }

    @Test
    void CardService_FindById_ReturnsModel(){
        when(cardRepositoryJpa.findById(any(UUID.class))).thenReturn(Optional.of(card1));

        CardDto foundCard = cardServiceImpl.findById(UUID.randomUUID());

        assertNotNull(foundCard);
        assertEquals(cardDto.getAccountNumber(), foundCard.getAccountNumber());
        assertEquals(cardDto.getCreatedDate(), foundCard.getCreatedDate());
        assertEquals(cardDto.getUpdatedDate(), foundCard.getUpdatedDate());
        assertEquals(cardDto.getCardNumber(), foundCard.getCardNumber());
        assertEquals(cardDto.getExpiryDate(), foundCard.getExpiryDate());
        assertEquals(cardDto.getStatus(), foundCard.getStatus());
    }

    @Test
    void AccountService_FindByNumber_ReturnsModel(){
        when(cardRepositoryJpa.findByCardNumber(any(String.class))).thenReturn(Optional.of(card1));

        CardDto foundCard = cardServiceImpl.findByNumber("");

        assertNotNull(foundCard);
        assertEquals(cardDto.getAccountNumber(), foundCard.getAccountNumber());
        assertEquals(cardDto.getCreatedDate(), foundCard.getCreatedDate());
        assertEquals(cardDto.getUpdatedDate(), foundCard.getUpdatedDate());
        assertEquals(cardDto.getCardNumber(), foundCard.getCardNumber());
        assertEquals(cardDto.getExpiryDate(), foundCard.getExpiryDate());
        assertEquals(cardDto.getStatus(), foundCard.getStatus());
    }

    @Test
    void AccountService_Save_ReturnsSavedModel(){
        when(cardRepositoryJpa.save(any(Card.class))).thenReturn(card1);

        CardDto savedCard = cardServiceImpl.create(cardDto);

        assertNotNull(savedCard);
        assertEquals(cardDto.getAccountNumber(), savedCard.getAccountNumber());
        assertEquals(cardDto.getCreatedDate(), savedCard.getCreatedDate());
        assertEquals(cardDto.getUpdatedDate(), savedCard.getUpdatedDate());
        assertEquals(cardDto.getCardNumber(), savedCard.getCardNumber());
        assertEquals(cardDto.getExpiryDate(), savedCard.getExpiryDate());
        assertEquals(cardDto.getStatus(), savedCard.getStatus());
    }

    @Test
    void AccountService_Edit_ReturnsEditedModel(){
        when(cardRepositoryJpa.save(any(Card.class))).thenReturn(card1);
        when(cardRepositoryJpa.findById(any(UUID.class))).thenReturn(Optional.of(card1));

        CardDto savedCard = cardServiceImpl.create(cardDto);

        savedCard.setStatus(CardStatus.UNREACHABLE);
        savedCard.setAccountNumber("IE12BOFI90000112346666");
        savedCard.setCreatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(5)));
        savedCard.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(3)));
        savedCard.setCardNumber("5425233430109333");
        savedCard.setExpiryDate(Date.valueOf(LocalDate.now().plusYears(4)));


        CardDto updatedCard = cardServiceImpl.edit(savedCard);

        assertNotNull(updatedCard);
        assertEquals(savedCard.getStatus(), updatedCard.getStatus());
        assertEquals(savedCard.getAccountNumber(), updatedCard.getAccountNumber());
        assertEquals(savedCard.getCreatedDate(), updatedCard.getCreatedDate());
        assertEquals(savedCard.getUpdatedDate(), updatedCard.getUpdatedDate());
        assertEquals(savedCard.getCardNumber(), updatedCard.getCardNumber());
        assertEquals(savedCard.getExpiryDate(), updatedCard.getExpiryDate());
    }



    @Test
    void AccountService_Delete_DeletesModel(){
        when(cardRepositoryJpa.findById(any(UUID.class))).thenReturn(Optional.empty());
        doNothing().when(cardRepositoryJpa).deleteById(isA(UUID.class));

        cardServiceImpl.delete(UUID.randomUUID());

        UUID uuid = UUID.randomUUID();

        assertThrows(ResourceNotFoundException.class, () -> cardServiceImpl.findById(uuid));
    }
}
