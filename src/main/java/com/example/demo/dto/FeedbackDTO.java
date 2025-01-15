package com.example.demo.dto;

public class FeedbackDTO {
    private String sourceText;
    private String translatedText;

    private String editedTranslatedText;
    private String sourceLanguage;
    private String targetLanguage;
    private String rating;

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
// Getters and Setters
    // ...

    public String getEditedTranslatedText() {
        return editedTranslatedText;
    }

    public void setEditedTranslatedText(String editedTranslatedText) {
        this.editedTranslatedText = editedTranslatedText;
    }
}
