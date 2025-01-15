package drlugha.user_app.dto;

import java.util.List;


public class BatchDTO {

    private Long batchNo;

    private String source;

    private String linkUrl;

    private String description;

    private Long uploaderId;

    private List<CreateSentenceDTO> sentences;

    public BatchDTO() {
    }

    public Long getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Long batchNo) {
        this.batchNo = batchNo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(Long uploaderId) {
        this.uploaderId = uploaderId;
    }

    public List<CreateSentenceDTO> getSentences() {
        return sentences;
    }

    public void setSentences(List<CreateSentenceDTO> sentences) {
        this.sentences = sentences;
    }

    @Override
    public String toString() {
        return "BatchDTO{" +
                "batchNo=" + batchNo +
                ", source='" + source + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", description='" + description + '\'' +
                ", uploaderId=" + uploaderId +
                ", sentences=" + sentences +
                '}';
    }
}
