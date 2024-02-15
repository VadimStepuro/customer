package com.stepuro.customer.repository;

import com.stepuro.customer.model.Individual;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividualRepositoryJpa extends JpaRepository<Individual, Integer> {
}
