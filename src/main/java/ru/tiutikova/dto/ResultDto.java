package ru.tiutikova.dto;

public class ResultDto extends SimpleDto {

    private boolean success;

    private String message;

    public ResultDto(boolean success) {
        this.success = success;
    }

    public ResultDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
