package com.stepuro.customer.repository;

import com.stepuro.customer.model.LegalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalEntityRepositoryJpa extends JpaRepository<LegalEntity, Integer> {
}
