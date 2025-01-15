package com.example.demo.service;

import com.example.demo.dto.PartnerDTO;
import com.example.demo.entity.Partner;
import com.example.demo.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public Long getPartnerIdFromDatabase(Long id) {
        // Check if the provided ID is null
        if (id == null) {
            throw new IllegalArgumentException("Partner ID cannot be null");
        }

        // Retrieve an existing partner from the database by ID
        Optional<Partner> optionalPartner = partnerRepository.findById(id);

        // Check if the partner exists
        if (optionalPartner.isPresent()) {
            // If a partner exists, return its ID
            return optionalPartner.get().getId();
        } else {
            // If no partner exists, return null or throw an exception
            return null; // or throw new PartnerNotFoundException("No partner found in the database with ID: " + id);
        }
    }



    public PartnerDTO getPartnerByIdFromDatabase(Long id) {
        // Retrieve partner entity from the database
        Optional<Partner> optionalPartner = partnerRepository.findById(id);

        // Check if the partner entity exists
        if (optionalPartner.isPresent()) {
            // Convert the partner entity to a DTO and return
            return convertToDTO(optionalPartner.get());
        } else {
            // Partner not found, return null or throw an exception
            return null; // or throw new PartnerNotFoundException("Partner not found with ID: " + id);
        }
    }



    public PartnerDTO createPartner(PartnerDTO partnerDTO) {
        Partner partner = new Partner();
        partner.setPartnerName(partnerDTO.getPartnerName());
        partner.setTitle(partnerDTO.getTitle());
        partner.setProfile(partnerDTO.getProfile());
        partner.setLinkedinUrl(partnerDTO.getLinkedinUrl());
        partner.setTwitterUrl(partnerDTO.getTwitterUrl());
        partner.setFacebookUrl(partnerDTO.getFacebookUrl());
        partner.setImageUrl(partnerDTO.getImageUrl());
        partner.setImageUrlExpiration(partnerDTO.getImageUrlExpiration());

        Partner savedPartner = partnerRepository.save(partner);

        partnerDTO.setId(savedPartner.getId());
        return partnerDTO;
    }

    public List<PartnerDTO> getAllPartners() {
        List<Partner> partners = partnerRepository.findAll();
        // Convert entities to DTOs
        return partners.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PartnerDTO getPartnerById(Long id) {
        Optional<Partner> partner = partnerRepository.findById(id);
        return partner.map(this::convertToDTO).orElse(null);
    }



    public void updatePartner(PartnerDTO partnerDTO) {
        Optional<Partner> existingPartner = partnerRepository.findById(partnerDTO.getId());
        if (existingPartner.isPresent()) {
            Partner partner = existingPartner.get();
            partner.setPartnerName(partnerDTO.getPartnerName());
            partner.setTitle(partnerDTO.getTitle());
            partner.setProfile(partnerDTO.getProfile());
            partner.setLinkedinUrl(partnerDTO.getLinkedinUrl());
            partner.setTwitterUrl(partnerDTO.getTwitterUrl());
            partner.setFacebookUrl(partnerDTO.getFacebookUrl());
            partner.setImageUrl(partnerDTO.getImageUrl());
            partner.setImageUrlExpiration(partnerDTO.getImageUrlExpiration());

            partnerRepository.save(partner);
        }
    }

    private PartnerDTO convertToDTO(Partner partner) {
        PartnerDTO partnerDTO = new PartnerDTO();
        partnerDTO.setId(partner.getId());
        partnerDTO.setPartnerName(partner.getPartnerName());
        partnerDTO.setTitle(partner.getTitle());
        partnerDTO.setProfile(partner.getProfile());
        partnerDTO.setLinkedinUrl(partner.getLinkedinUrl());
        partnerDTO.setTwitterUrl(partner.getTwitterUrl());
        partnerDTO.setFacebookUrl(partner.getFacebookUrl());
        partnerDTO.setImageUrl(partner.getImageUrl());
        partnerDTO.setImageUrlExpiration(partner.getImageUrlExpiration());
        return partnerDTO;
    }
}

