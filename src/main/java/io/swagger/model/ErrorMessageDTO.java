package io.swagger.model;

public class ErrorMessageDTO {
    private String message;

    public ErrorMessageDTO(String reason) {
        this.message = reason;
    }

    public String getReason() {
        return message;
    }

    public void setReason(String reason) {
        this.message = reason;
    }
}
