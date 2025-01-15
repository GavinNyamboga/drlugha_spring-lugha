package drlugha.user_app.dto;


public class CreateSentenceDTO {

    private String sentenceText;

    private String language;

    public CreateSentenceDTO() {
    }

    public CreateSentenceDTO(String sentenceText, String language) {
        this.sentenceText = sentenceText;
        this.language = language;
    }

    public String getSentenceText() {
        return sentenceText;
    }

    public void setSentenceText(String sentenceText) {
        this.sentenceText = sentenceText;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "CreateSentenceDTO{" +
                "sentenceText='" + sentenceText + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
