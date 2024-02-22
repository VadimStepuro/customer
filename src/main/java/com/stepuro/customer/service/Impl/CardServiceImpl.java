package com.stepuro.customer.service.Impl;

import com.stepuro.customer.api.dto.CardDto;
import com.stepuro.customer.api.dto.mapper.CardMapper;
import com.stepuro.customer.api.dto.mapper.IndividualMapper;
import com.stepuro.customer.api.exceptions.NoContentException;
import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import com.stepuro.customer.model.Card;
import com.stepuro.customer.repository.CardRepositoryJpa;
import com.stepuro.customer.service.CardService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepositoryJpa cardRepositoryJpa;

    @Override
    public List<CardDto> findAll(){
        List<CardDto> cardDtos = cardRepositoryJpa
                .findAll()
                .stream()
                .map(CardMapper.INSTANCE::cardToCardDto)
                .toList();

        if(cardDtos.isEmpty())
            throw new NoContentException("No cards found");

        return cardDtos;
    }

    @Override
    public CardDto findById(UUID id){
        return CardMapper
                .INSTANCE
                .cardToCardDto(
                        cardRepositoryJpa
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Card with id " + id + " not found"))
                );
    }

    @Override
    public CardDto findByCardNumber(String cardNumber) {
        return CardMapper
                .INSTANCE
                .cardToCardDto(
                        cardRepositoryJpa
                                .findByCardNumber(cardNumber)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                        "Card with card number" +
                                                cardNumber +
                                                " not found")));
    }

    @Override
    public boolean existsByCardNumber(String cardNumber) {
        return cardRepositoryJpa.existsByCardNumber(cardNumber);
    }

    @Override
    public boolean checkCardOwner(String cardNumber, Integer individualId) {
        Card foundCard = cardRepositoryJpa
                .findByCardNumber(cardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card with card number" + cardNumber + " not found"));

        if(foundCard.getIndividual() == null)
            return false;

        return foundCard.getIndividual().getIndividualId().equals(individualId);
    }

    @Override
    public boolean validateCardBalance(String cardNumber, BigDecimal amount) {
        Card foundCard = cardRepositoryJpa
                .findByCardNumber(cardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card with card number" + cardNumber + " not found"));

        int result = foundCard.getBalance().compareTo(amount);

        return result >= 0;
    }


    @Override
    @Transactional
    public CardDto create(CardDto cardDto){
        return CardMapper
                .INSTANCE
                .cardToCardDto(
                        cardRepositoryJpa
                                .save(CardMapper
                                        .INSTANCE
                                        .cardDtoToCard(cardDto))
                );
    }

    @Override
    @Transactional
    public CardDto edit(CardDto cardDto){
        Card card = cardRepositoryJpa
                .findById(cardDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Card with id " + cardDto.getId() + " not found"));

        card.setAccountNumber(cardDto.getAccountNumber());
        card.setCardNumber(cardDto.getCardNumber());
        card.setCreatedDate(cardDto.getCreatedDate());
        card.setUpdatedDate(cardDto.getUpdatedDate());
        card.setStatus(cardDto.getStatus());
        card.setBalance(cardDto.getBalance());
        card.setIndividual(
                IndividualMapper
                        .INSTANCE
                        .individualDtoToIndividual(cardDto.getIndividualDto()));
        card.setExpiryDate(cardDto.getExpiryDate());

        return CardMapper
                .INSTANCE
                .cardToCardDto(cardRepositoryJpa.save(card));
    }

    @Override
    @Transactional
    public void delete(UUID id){
        cardRepositoryJpa.deleteById(id);
    }
}
