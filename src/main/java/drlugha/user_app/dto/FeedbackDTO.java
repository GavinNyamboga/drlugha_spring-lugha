package drlugha.user_app.dto;

public class FeedbackDTO {
    private String sourceText;
    private String translatedText;
    private String editedTranslatedText;
    private String sourceLanguage;
    private Long sourceLanguageId;
    private String targetLanguage;
    private Long targetLanguageId;
    private String rating;
    private Integer score;

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

    public String getEditedTranslatedText() {
        return editedTranslatedText;
    }

    public void setEditedTranslatedText(String editedTranslatedText) {
        this.editedTranslatedText = editedTranslatedText;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getSourceLanguageId() {
        return sourceLanguageId;
    }

    public void setSourceLanguageId(Long sourceLanguageId) {
        this.sourceLanguageId = sourceLanguageId;
    }

    public Long getTargetLanguageId() {
        return targetLanguageId;
    }

    public void setTargetLanguageId(Long targetLanguageId) {
        this.targetLanguageId = targetLanguageId;
    }
}
