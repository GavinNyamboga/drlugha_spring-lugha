package drlugha.user_app.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {

    @Value("${amazonProperties.bucket.endpointUrl}")
    private String endpointUrl;

    @Value("${amazonProperties.bucket.accessKey}")
    private String accessKey;

    @Value("${amazonProperties.bucket.secretKey}")
    private String secretKey;

    @Value("${amazonProperties.partner.bucketName}")
    private String partnerBucketName;

    @Value("${amazonProperties.article.bucketName}")
    private String articleBucketName;

    @Bean(name = "partnerAmazonS3Client")
    public AmazonS3 partnerAmazonS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUrl, Regions.US_EAST_1.getName()))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    @Bean(name = "articleAmazonS3Client")
    public AmazonS3 articleAmazonS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUrl, Regions.US_EAST_1.getName()))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
