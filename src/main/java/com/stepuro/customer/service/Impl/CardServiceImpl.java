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

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

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
    @Transactional
    public void transferAmount(TransferEntity transferEntity) {
        validateTransfer(transferEntity);

        Card sourceCard = findCardByNumber(transferEntity.getSourceNumber());

        Card destinationCard = findCardByNumber(transferEntity.getDestinationNumber());

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

    private void validateTransfer(TransferEntity transferEntity){
        if(transferEntity.getSourceNumber().equals(transferEntity.getDestinationNumber()))
            throw new EqualNumberException("Card numbers for transfer can't be equals");

        if (!validateCardOwner(transferEntity.getSourceNumber(), transferEntity.getUserId()))
            throw new UserIdDoesntMatchException("Individual isn't owner of the card");

        if(!validateCardBalance(transferEntity.getSourceNumber(), transferEntity.getAmount()))
            throw new NotEnoughMoneyException("Not enough money on card with number " + transferEntity.getSourceNumber() +
                    " to transfer " + transferEntity.getAmount());

    }

    private boolean isCardNumber(String number){
        Pattern pattern = Pattern.compile("^(?:4[0-9]{12}(?:[0-9]{3})?|(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|6(?:011|5[0-9]{2})[0-9]{12}|(?:2131|1800|35\\d{3})\\d{11})$");

        return pattern.matcher(number).matches();
    }

    private boolean validateSourceAndDestinationNumbers(String sourceNumber, String destinationNumber){
        return !sourceNumber.equals(destinationNumber);
    }

    private Card findCardByNumber(String cardNumber){
        if(isCardNumber(cardNumber))
            return cardRepositoryJpa
                    .findByCardNumber(cardNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("Card with card number" + cardNumber + " not found"));
        else
            return cardRepositoryJpa
                    .findByAccountNumber(cardNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("Card with account number" + cardNumber + " not found"));
    }

    private boolean existsByCardNumber(String cardNumber) {
        if(isCardNumber(cardNumber))
            return cardRepositoryJpa.existsByCardNumber(cardNumber);
        else
            return cardRepositoryJpa.existsByAccountNumber(cardNumber);
    }

    private boolean validateCardOwner(String cardNumber, Integer individualId) {
        Card foundCard = findCardByNumber(cardNumber);

        if(foundCard.getIndividual() == null)
            return false;

        return foundCard.getIndividual().getIndividualId().equals(individualId);
    }

    private boolean validateCardBalance(String cardNumber, BigDecimal amount) {
        Card foundCard = findCardByNumber(cardNumber);

        int result = foundCard.getBalance().compareTo(amount);

        return result >= 0;
    }
}
