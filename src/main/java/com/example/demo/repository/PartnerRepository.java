package com.example.demo.repository;

import com.example.demo.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    // You can add custom query methods here if needed
    // Find the top partner by ordering by ID in descending order
    Optional<Partner> findTopByOrderByIdDesc();
    Optional<Partner> findFirstById(Long id);
}
