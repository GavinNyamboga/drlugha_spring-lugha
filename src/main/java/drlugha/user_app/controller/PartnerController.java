package drlugha.user_app.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import drlugha.user_app.dto.PartnerDTO;
import drlugha.user_app.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/partners")
public class PartnerController {

    private final PartnerService partnerService;
    private final AmazonS3 amazonS3;

    @Autowired
    public PartnerController(PartnerService partnerService, @Qualifier("partnerAmazonS3Client") AmazonS3 amazonS3) {
        this.partnerService = partnerService;
        this.amazonS3 = amazonS3;
    }

    @Value("${amazonProperties.partner.bucketName}")
    private String bucketName;

    @PostMapping
    public ResponseEntity<PartnerDTO> createPartner(@RequestParam("partnerName") String partnerName,
                                                    @RequestParam("title") String title,
                                                    @RequestParam("profile") String profile,
                                                    @RequestParam("linkedinUrl") String linkedinUrl,
                                                    @RequestParam("twitterUrl") String twitterUrl,
                                                    @RequestParam("facebookUrl") String facebookUrl,
                                                    @RequestParam("partnerImage") MultipartFile partnerImage) throws IOException {
        // Generate unique file name
        String fileName = UUID.randomUUID().toString() + "-" + partnerImage.getOriginalFilename();

        // Upload image to Amazon S3
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(partnerImage.getSize());
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, partnerImage.getInputStream(), metadata));

        // Generate pre-signed URL for the uploaded image
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName);
        // Set expiration time to 1 hour (3600 seconds) from the current time
        generatePresignedUrlRequest.setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000));
        URL imageUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        // Create PartnerDTO and set image URL
        PartnerDTO partnerDTO = new PartnerDTO();
        partnerDTO.setPartnerName(partnerName);
        partnerDTO.setTitle(title);
        partnerDTO.setProfile(profile);
        partnerDTO.setLinkedinUrl(linkedinUrl);
        partnerDTO.setTwitterUrl(twitterUrl);
        partnerDTO.setFacebookUrl(facebookUrl);
        partnerDTO.setImageUrl(imageUrl.toString());
        partnerDTO.setImageUrlExpiration(generatePresignedUrlRequest.getExpiration());

        // Save partner to database
        PartnerDTO createdPartner = partnerService.createPartner(partnerDTO);

        // Return the created partner in the response
        return ResponseEntity.ok().body(createdPartner);
    }

    @GetMapping
    public ResponseEntity<List<PartnerDTO>> getPartners() {
        List<PartnerDTO> partners = partnerService.getAllPartners();

        // Do not manually set the IDs here. The IDs should come from the database.

        return ResponseEntity.ok().body(partners);
    }


    @GetMapping("/getPartnerId")
    public ResponseEntity<Long> getPartnerId(@RequestParam("id") Long partnerId) {
        // Check if the provided partner ID is not null
        if (partnerId != null) {
            Long retrievedPartnerId = partnerService.getPartnerIdFromDatabase(partnerId);
            if (retrievedPartnerId != null) {
                return ResponseEntity.ok().body(retrievedPartnerId);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            // Handle the case where the partner ID is null
            return ResponseEntity.badRequest().body(null); // You can choose to return a different HTTP status code or message here
        }
    }




    @GetMapping("/getPartnerById/{id}")
    public ResponseEntity<PartnerDTO> getPartnerById(@PathVariable("id") Long id) {
        // Retrieve the partner details from the database using the provided ID
        PartnerDTO partner = partnerService.getPartnerByIdFromDatabase(id);
        if (partner != null) {
            return ResponseEntity.ok().body(partner);
        } else {
            return ResponseEntity.notFound().build();
        }
    }







    @GetMapping("/{id}/renew-url")
    public ResponseEntity<PartnerDTO> renewImageUrl(@PathVariable("id") Long id) {
        System.out.println("Renewing image URL for partner with ID: " + id);
        PartnerDTO partner = partnerService.getPartnerById(id);
        if (partner == null) {
            System.out.println("Partner not found with ID: " + id);
            return ResponseEntity.notFound().build();
        }

        // Extract the object key from the current image URL
        String imageUrl = partner.getImageUrl();
        String objectKey = extractObjectKeyFromUrl(imageUrl);

        // Generate a new URL and update the partner's image URL
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey);

        // Set expiration time to 7 days from the current time
        Date expirationTime = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000); // 7 days expiration
        generatePresignedUrlRequest.setExpiration(expirationTime);

        // Generate a new pre-signed URL
        URL newImageUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        // Update the partner with the new URL and expiration time
        partner.setImageUrl(newImageUrl.toString());
        partner.setImageUrlExpiration(expirationTime); // Assuming you have a field to store the URL expiration in the PartnerDTO

        // Save the updated partner to the database
        partnerService.updatePartner(partner);

        // Log the new URL for debugging
        System.out.println("Generated new presigned URL: " + newImageUrl.toString());

        return ResponseEntity.ok().body(partner);
    }

    // Helper method to extract the object key from the URL
    private String extractObjectKeyFromUrl(String url) {
        try {
            URL u = new URL(url);
            return u.getPath().substring(1); // Remove the leading slash
        } catch (Exception e) {
            throw new RuntimeException("Invalid image URL format");
        }
    }
}




