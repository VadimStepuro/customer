package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.CardDto;
import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import com.stepuro.customer.model.Card;
import com.stepuro.customer.model.status.CardStatus;
import com.stepuro.customer.repository.CardRepositoryJpa;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardServiceTests {
    @Mock
    private CardRepositoryJpa cardRepositoryJpa;

    @InjectMocks
    private CardService cardService;

    @Test
    public void CardService_FindAll_ReturnsAllModels(){
        //Arrange
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

        List<Card> cards = List.of(card1, card2, card3, card4);

        when(cardRepositoryJpa.findAll()).thenReturn(cards);

        //Act
        List<CardDto> allCards = cardService.findAll();

        //Assert
        assertNotNull(allCards);
        assertEquals(4, allCards.size());
    }

    @Test
    public void CardService_FindById_ReturnsModel(){
        CardDto cardDto = CardDto.builder()
                .accountNumber("IE12BOFI90000112345678")
                .cardNumber("5425233430109903")
                .status(CardStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
                .build();

        Card card = Card.builder()
                .accountNumber("IE12BOFI90000112345678")
                .cardNumber("5425233430109903")
                .status(CardStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
                .build();

        when(cardRepositoryJpa.findById(any(UUID.class))).thenReturn(Optional.of(card));

        CardDto foundCard = cardService.findById(UUID.randomUUID());

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
        CardDto cardDto = CardDto.builder()
                .accountNumber("IE12BOFI90000112345678")
                .cardNumber("5425233430109903")
                .status(CardStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
                .build();

        Card card = Card.builder()
                .accountNumber("IE12BOFI90000112345678")
                .cardNumber("5425233430109903")
                .status(CardStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
                .build();

        when(cardRepositoryJpa.save(any(Card.class))).thenReturn(card);

        CardDto savedCard = cardService.create(cardDto);

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
        CardDto cardDto = CardDto.builder()
                .id(UUID.randomUUID())
                .accountNumber("IE12BOFI90000112345678")
                .cardNumber("5425233430109903")
                .status(CardStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
                .build();

        Card card = Card.builder()
                .id(cardDto.getId())
                .accountNumber("IE12BOFI90000112345678")
                .cardNumber("5425233430109903")
                .status(CardStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
                .build();

        when(cardRepositoryJpa.save(any(Card.class))).thenReturn(card);
        when(cardRepositoryJpa.findById(any(UUID.class))).thenReturn(Optional.of(card));

        CardDto savedCard = cardService.create(cardDto);

        savedCard.setStatus(CardStatus.UNREACHABLE);
        savedCard.setAccountNumber("IE12BOFI90000112346666");
        savedCard.setCreatedDate(Date.valueOf(LocalDate.now().minusMonths(5)));
        savedCard.setUpdatedDate(Date.valueOf(LocalDate.now().minusMonths(3)));
        savedCard.setCardNumber("5425233430109333");
        savedCard.setExpiryDate(Date.valueOf(LocalDate.now().plusYears(4)));


        CardDto updatedCard = cardService.edit(savedCard);

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

        cardService.delete(UUID.randomUUID());

        assertThrows(ResourceNotFoundException.class, () -> cardService.findById(UUID.randomUUID()));
    }
}
