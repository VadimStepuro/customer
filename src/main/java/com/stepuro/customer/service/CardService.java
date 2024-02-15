package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.CardDto;
import com.stepuro.customer.api.dto.mapper.CardMapper;
import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import com.stepuro.customer.model.Card;
import com.stepuro.customer.repository.CardRepositoryJpa;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CardService {
    @Autowired
    private CardRepositoryJpa cardRepositoryJpa;

    public List<CardDto> findAll(){
        return cardRepositoryJpa
                .findAll()
                .stream()
                .map(CardMapper.INSTANCE::cardToCardDto)
                .toList();
    }

    public CardDto findById(UUID id){
        return CardMapper
                .INSTANCE
                .cardToCardDto(
                        cardRepositoryJpa
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Card with id " + id + " not found"))
                );
    }

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

    public CardDto edit(CardDto cardDto){
        Card card = cardRepositoryJpa
                .findById(cardDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Card with id " + cardDto.getId() + " not found"));

        card.setAccountNumber(cardDto.getAccountNumber());
        card.setCardNumber(cardDto.getCardNumber());
        card.setCreatedDate(cardDto.getCreatedDate());
        card.setUpdatedDate(cardDto.getUpdatedDate());
        card.setStatus(cardDto.getStatus());
        card.setIndividual(cardDto.getIndividual());
        card.setExpiryDate(cardDto.getExpiryDate());

        return CardMapper
                .INSTANCE
                .cardToCardDto(cardRepositoryJpa.save(card));
    }

    public void delete(UUID id){
        cardRepositoryJpa.deleteById(id);
    }
}
