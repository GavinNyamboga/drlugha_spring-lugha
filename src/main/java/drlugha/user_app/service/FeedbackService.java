package drlugha.user_app.service;

import drlugha.user_app.dto.FeedbackDTO;
import drlugha.user_app.entity.Feedback;
import drlugha.user_app.entity.Language;
import drlugha.user_app.enums.YesNo;
import drlugha.user_app.repository.FeedbackRepository;
import drlugha.user_app.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final LanguageRepository languageRepository;


    @Value("${drlugha.translate.apiUrl}")
    private String translateApiUrl;

    public FeedbackService(FeedbackRepository feedbackRepository, LanguageRepository languageRepository) {
        this.feedbackRepository = feedbackRepository;
        this.languageRepository = languageRepository;
    }

    public Feedback saveFeedback(FeedbackDTO feedbackDTO) {

        //support legacy implementation
        if (feedbackDTO.getSourceLanguageId() == null && feedbackDTO.getSourceLanguage() != null)
            feedbackDTO.setSourceLanguageId(Long.valueOf(feedbackDTO.getSourceLanguage()));

        if (feedbackDTO.getTargetLanguageId() == null && feedbackDTO.getTargetLanguage() != null)
            feedbackDTO.setTargetLanguageId(Long.valueOf(feedbackDTO.getTargetLanguage()));

        Language sourceLanguage = languageRepository.findById(feedbackDTO.getSourceLanguageId()).orElse(null);
        if (sourceLanguage == null)
            throw new RuntimeException("Source Language not found");

        Language targetLanguage = languageRepository.findById(feedbackDTO.getTargetLanguageId()).orElse(null);
        if (targetLanguage == null)
            throw new RuntimeException("Target Language not found");

        Feedback feedback = new Feedback();
        feedback.setSourceText(feedbackDTO.getSourceText());
        feedback.setTranslatedText(feedbackDTO.getTranslatedText());
        feedback.setSourceLanguageId(feedbackDTO.getSourceLanguageId());
        feedback.setEditedTranslatedText(feedbackDTO.getEditedTranslatedText()); // Set edited translated text
        feedback.setTargetLanguageId(feedbackDTO.getTargetLanguageId());
        //feedback.setRating(feedbackDTO.getRating());
        feedback.setRatingScore(feedbackDTO.getRatingScore());
        feedback.setSentToTranslationApp(YesNo.NO);
        feedback = feedbackRepository.save(feedback);

        //only send to batch if the translated text is edited
        if (feedbackDTO.getEditedTranslatedText() != null && !feedbackDTO.getEditedTranslatedText().isEmpty()) {
            try {
                feedbackDTO.setTargetLanguage(targetLanguage.getName());
                feedbackDTO.setSourceLanguage(sourceLanguage.getName());

                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<Object> request = new HttpEntity<>(feedbackDTO);

                ResponseEntity<?> response = restTemplate.exchange(
                        translateApiUrl + "/batch/feedback",
                        HttpMethod.POST,
                        request,
                        Object.class
                );
                if (response.getStatusCode() == HttpStatus.OK) {
                    feedback.setSentToTranslationApp(YesNo.YES);
                    feedback = feedbackRepository.save(feedback);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return feedback;
    }
}
