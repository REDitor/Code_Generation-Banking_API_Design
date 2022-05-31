package io.swagger.model;

public class ExceptionDTO {

    private String reason;

    public ExceptionDTO() {
    }

    public ExceptionDTO(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
