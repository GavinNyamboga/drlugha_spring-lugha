package com.example.demo.service;

import com.example.demo.dto.FeedbackDTO;
import com.example.demo.entity.Feedback;
import com.example.demo.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback saveFeedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setSourceText(feedbackDTO.getSourceText());
        feedback.setTranslatedText(feedbackDTO.getTranslatedText());
        feedback.setSourceLanguage(feedbackDTO.getSourceLanguage());
        feedback.setEditedTranslatedText(feedbackDTO.getEditedTranslatedText()); // Set edited translated text
        feedback.setTargetLanguage(feedbackDTO.getTargetLanguage());
        feedback.setRating(feedbackDTO.getRating());
        return feedbackRepository.save(feedback);
    }
}
