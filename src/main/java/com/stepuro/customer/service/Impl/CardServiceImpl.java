package com.stepuro.customer.service.Impl;

import com.stepuro.customer.api.dto.CardDto;
import com.stepuro.customer.api.dto.TransferEntity;
import com.stepuro.customer.api.dto.mapper.CardMapper;
import com.stepuro.customer.api.dto.mapper.IndividualMapper;
import com.stepuro.customer.api.exceptions.*;
import com.stepuro.customer.model.Card;
import com.stepuro.customer.repository.CardRepositoryJpa;
import com.stepuro.customer.repository.IndividualRepositoryJpa;
import com.stepuro.customer.service.CardService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.stepuro.customer.utils.CardUtils.*;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepositoryJpa cardRepositoryJpa;

    @Autowired
    private IndividualRepositoryJpa individualRepositoryJpa;

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
    public boolean existsByNumber(String cardNumber) {
        if(isAccountNumber(cardNumber))
            return cardRepositoryJpa.existsByAccountNumber(cardNumber);
        else
            return cardRepositoryJpa.existsByCardNumber(cardNumber);
    }

    @Override
    @Transactional
    public void transferAmount(TransferEntity transferEntity) {
        Card sourceCard = findCardByNumber(transferEntity.getSourceNumber());

        Card destinationCard = findCardByNumber(transferEntity.getDestinationNumber());

        validateTransfer(transferEntity, sourceCard);

        sourceCard.setBalance(sourceCard.getBalance().subtract(transferEntity.getAmount()));
        destinationCard.setBalance(destinationCard.getBalance().add(transferEntity.getAmount()));

        cardRepositoryJpa.save(sourceCard);
        cardRepositoryJpa.save(destinationCard);
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

    private void validateTransfer(TransferEntity transferEntity, Card sourceCard){
        if(transferEntity.getSourceNumber().equals(transferEntity.getDestinationNumber()))
            throw new EqualNumberException("Card numbers can't be equal " +
                    "(source number: " + transferEntity.getSourceNumber() +
                    ", destination number: " + transferEntity.getDestinationNumber() + ")");

        if (!validateCardOwner(sourceCard, transferEntity.getUserId()))
            throw new UserIdDoesntMatchException("Individual isn't owner of the card " +
                    "(individual id: " + transferEntity.getUserId() +
                    ", card number: " + transferEntity.getSourceNumber() + ")");

        if(!validateCardBalance(sourceCard, transferEntity.getAmount()))
            throw new NotEnoughMoneyException("Not enough money on card with number " + transferEntity.getSourceNumber() +
                    " to transfer " + transferEntity.getAmount());

    }

    private Card findCardByNumber(String cardNumber){
        if(isAccountNumber(cardNumber))
            return cardRepositoryJpa
                    .findByAccountNumber(cardNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("Card with account number" + cardNumber + " not found"));
        else
            return cardRepositoryJpa
                    .findByCardNumber(cardNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("Card with card number" + cardNumber + " not found"));
    }
}
