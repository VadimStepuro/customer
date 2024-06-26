package com.stepuro.customer.service.impl;

import com.stepuro.customer.api.dto.CardDto;
import com.stepuro.customer.api.dto.TransferEntity;
import com.stepuro.customer.api.dto.mapper.CardMapper;
import com.stepuro.customer.api.dto.mapper.IndividualMapper;
import com.stepuro.customer.api.exceptions.*;
import com.stepuro.customer.model.Card;
import com.stepuro.customer.repository.CardRepositoryJpa;
import com.stepuro.customer.service.CardService;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.stepuro.customer.utils.CardUtils.*;

@Service
public class CardServiceImpl implements CardService {
    private final CardRepositoryJpa cardRepositoryJpa;

    public CardServiceImpl(CardRepositoryJpa cardRepositoryJpa) {
        this.cardRepositoryJpa = cardRepositoryJpa;
    }

    @Override
    @Cacheable(cacheNames = "cards",  keyGenerator = "newKeyGenerator")
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
    @Cacheable(cacheNames = "cardById", key = "#id")
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
    @Cacheable(cacheNames = "cardByNumber", key = "#cardNumber")
    public CardDto findByNumber(String cardNumber) {
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
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "cards", allEntries = true),
                    @CacheEvict(cacheNames = "cardById", allEntries = true),
                    @CacheEvict(cacheNames = "cardByNumber", key = "#transferEntity.sourceNumber"),
                    @CacheEvict(cacheNames = "cardByNumber", key = "#transferEntity.destinationNumber")
            }
    )
    public void transferAmount(TransferEntity transferEntity) {
        Card sourceCard = findCardByNumber(transferEntity.getSourceNumber());
        Card destinationCard = findCardByNumber(transferEntity.getDestinationNumber());

        sourceCard.setBalance(sourceCard.getBalance().subtract(transferEntity.getAmount()));
        destinationCard.setBalance(destinationCard.getBalance().add(transferEntity.getAmount()));

        cardRepositoryJpa.save(sourceCard);
        cardRepositoryJpa.save(destinationCard);
    }


    @Override
    @Transactional
    @Caching(
            put = {
                    @CachePut(cacheNames = "cardByNumber", key = "#cardDto.cardNumber")
            },
            evict = {
                    @CacheEvict(cacheNames = "cards", allEntries = true)
            }
    )
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
    @Caching(
            put = {
                    @CachePut(cacheNames = "cardById", key = "#cardDto.id"),
                    @CachePut(cacheNames = "cardByNumber", key = "#cardDto.cardNumber")
            },
            evict = {
                    @CacheEvict(cacheNames = "cards", allEntries = true)
            }
    )
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
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "cards", allEntries = true),
                    @CacheEvict(cacheNames = "cardByNumber", allEntries = true),
                    @CacheEvict(cacheNames = "cardById", key = "#id")
            }
    )
    public void delete(UUID id){
        cardRepositoryJpa.deleteById(id);
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
