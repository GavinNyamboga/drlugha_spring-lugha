package drlugha.user_app.entity;

import drlugha.user_app.enums.YesNo;

import javax.persistence.*;

@Table(name = "feedback")
@Entity
public class Feedback extends BaseEntity {

    @Column
    private String sourceText;

    @Column
    private String translatedText;

    @Column(name = "source_language_id")
    private Long sourceLanguageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_language_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Language sourceLanguage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_language_id", referencedColumnName = "id", insertable = false, updatable = false                                                                                                                                                                                                 )
    private Language targetLanguage;

    @Column(name = "target_language_id")
    private Long targetLanguageId;

    @Column
    private String rating;

    @Column
    private Integer ratingScore;

    @Column
    @Enumerated(EnumType.STRING)
    private YesNo sentToTranslationApp;

    @Column
    private String editedTranslatedText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getSourceLanguageId() {
        return sourceLanguageId;
    }

    public void setSourceLanguageId(Long sourceLanguageId) {
        this.sourceLanguageId = sourceLanguageId;
    }

    public Language getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(Language sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public Language getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(Language targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public Long getTargetLanguageId() {
        return targetLanguageId;
    }

    public void setTargetLanguageId(Long targetLanguageId) {
        this.targetLanguageId = targetLanguageId;
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

    public Integer getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(Integer ratingScore) {
        this.ratingScore = ratingScore;
    }

    public YesNo getSentToTranslationApp() {
        return sentToTranslationApp;
    }

    public void setSentToTranslationApp(YesNo sentToTranslationApp) {
        this.sentToTranslationApp = sentToTranslationApp;
    }
}

