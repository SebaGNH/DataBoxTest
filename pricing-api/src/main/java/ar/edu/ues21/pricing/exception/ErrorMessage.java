package ar.edu.ues21.pricing.exception;

public class ErrorMessage {

    private int code;
    private String message;

    public ErrorMessage() {

    }

    public ErrorMessage(String message) {
        super();
        this.message = message;
    }

    public ErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
