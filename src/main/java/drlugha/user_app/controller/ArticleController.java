package drlugha.user_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.AmazonS3;
import drlugha.user_app.dto.ArticleDTO;
import drlugha.user_app.service.ArticleService;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final AmazonS3 amazonS3;

    @Autowired
    public ArticleController(ArticleService articleService, @Qualifier("articleAmazonS3Client") AmazonS3 amazonS3) {
        this.articleService = articleService;
        this.amazonS3 = amazonS3;
    }

    @Value("${amazonProperties.article.bucketName}")
    private String bucketName;

    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestParam("id") Long userId,
                                                    @RequestParam("articleName") String articleName,
                                                    @RequestParam("articleCategory") String articleCategory,
                                                    @RequestParam("articleSubCategory") String articleSubCategory,
                                                    @RequestParam("articleTitle") String articleTitle,
                                                    @RequestParam("description") String description,
                                                    @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        if (userId == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Upload image to Amazon S3 and get pre-signed URL
        String imageUrl = uploadImageToS3(imageFile);

        // Create ArticleDTO and set user ID and image URL
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setUserId(userId);
        articleDTO.setArticleCategory(articleCategory);
        articleDTO.setArticleSubCategory(articleSubCategory);
        articleDTO.setArticleName(articleName);
        articleDTO.setArticleTitle(articleTitle);
        articleDTO.setDescription(description);
        articleDTO.setImageUrl(imageUrl);

        // Validate articleDTO and create the article
        ArticleDTO createdArticle = articleService.createArticle(articleDTO);

        // Return the created article in the response
        return ResponseEntity.ok().body(createdArticle);
    }

    private String uploadImageToS3(MultipartFile imageFile) throws IOException {
        // Generate a unique file name
        String fileName = UUID.randomUUID().toString() + "-" + StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));

        // Upload image to S3 bucket
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(imageFile.getSize());
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, imageFile.getInputStream(), metadata));

        // Generate presigned URL for the uploaded image
        return generatePresignedUrl(fileName, 7).toString();
    }

    private URL generatePresignedUrl(String fileName, int daysUntilExpiration) {
        Date expirationTime = new Date(System.currentTimeMillis() + daysUntilExpiration * 24 * 60 * 60 * 1000); // daysUntilExpiration days expiration
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName);
        generatePresignedUrlRequest.setExpiration(expirationTime);
        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
    }

    private String extractS3KeyFromUrl(String url) {
        // Logic to extract the S3 key from the URL, assuming the key starts after the bucket name
        // Example: https://bucket-name.s3.amazonaws.com/key-path
        try {
            URL parsedUrl = new URL(url);
            String path = parsedUrl.getPath();
            if (path != null && path.length() > 1) {
                return path.substring(1); // Remove the leading '/'
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/{id}/renew-url")
    public ResponseEntity<ArticleDTO> renewImageUrl(@PathVariable("id") Long id) {
        ArticleDTO article = articleService.getArticleById(id);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }

        // Extract the S3 key from the current image URL
        String s3Key = extractS3KeyFromUrl(article.getImageUrl());
        if (s3Key == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Generate a new pre-signed URL
        URL newImageUrl = generatePresignedUrl(s3Key, 7);

        // Update the article with the new URL
        article.setImageUrl(newImageUrl.toString());
        articleService.updateArticle(article);

        return ResponseEntity.ok().body(article);
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getArticles() {
        List<ArticleDTO> articles = articleService.getAllArticles();
        return ResponseEntity.ok().body(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable("id") Long id) {
        ArticleDTO article = articleService.getArticleById(id);
        if (article != null) {
            return ResponseEntity.ok().body(article);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

