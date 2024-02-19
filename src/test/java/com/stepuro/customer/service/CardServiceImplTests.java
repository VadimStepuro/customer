package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.CardDto;
import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import com.stepuro.customer.model.Card;
import com.stepuro.customer.model.enums.CardStatus;
import com.stepuro.customer.repository.CardRepositoryJpa;
import com.stepuro.customer.service.Impl.CardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
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
public class CardServiceImplTests {
    @Mock
    private CardRepositoryJpa cardRepositoryJpa;

    @InjectMocks
    private CardServiceImpl cardServiceImpl;

    @Test
    public void CardService_FindAll_ReturnsAllModels(){
        List<Card> cards = List.of(card1, card2, card3, card4);

        when(cardRepositoryJpa.findAll()).thenReturn(cards);

        //Act
        List<CardDto> allCards = cardServiceImpl.findAll();

        //Assert
        assertNotNull(allCards);
        assertEquals(4, allCards.size());
    }

    @Test
    public void CardService_FindById_ReturnsModel(){
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
    public void AccountService_Save_ReturnsSavedModel(){
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
    public void AccountService_Edit_ReturnsEditedModel(){
        when(cardRepositoryJpa.save(any(Card.class))).thenReturn(card1);
        when(cardRepositoryJpa.findById(any(UUID.class))).thenReturn(Optional.of(card1));

        CardDto savedCard = cardServiceImpl.create(cardDto);

        savedCard.setStatus(CardStatus.UNREACHABLE);
        savedCard.setAccountNumber("IE12BOFI90000112346666");
        savedCard.setCreatedDate(Date.valueOf(LocalDate.now().minusMonths(5)));
        savedCard.setUpdatedDate(Date.valueOf(LocalDate.now().minusMonths(3)));
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
    public void AccountService_Delete_DeletesModel(){
        when(cardRepositoryJpa.findById(any(UUID.class))).thenReturn(Optional.empty());
        doNothing().when(cardRepositoryJpa).deleteById(isA(UUID.class));

        cardServiceImpl.delete(UUID.randomUUID());

        assertThrows(ResourceNotFoundException.class, () -> cardServiceImpl.findById(UUID.randomUUID()));
    }
}
