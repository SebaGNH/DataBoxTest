package ar.edu.ues21.pricingbff.exception;

import org.springframework.http.HttpStatus;
import retrofit2.Response;

import java.io.IOException;
import java.util.Optional;

public class ProxyException extends RuntimeException {

    /**
    *
    */
    private static final long serialVersionUID = -2763070437520950783L;
    private String body;
    private final HttpStatus code;

    public ProxyException(Response<?> r) {
        try {
            this.body = r.errorBody().string();
        } catch (IOException e) {
            this.body = "Error calling service"+e.getMessage();
        }
        this.code = findByCode(r.code());
    }

    public String getBody() {
        return body;
    }

    private HttpStatus findByCode(final int code) {
        return Optional.ofNullable(HttpStatus.resolve(code)).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpStatus getCode() {
        return code;
    }


    


}