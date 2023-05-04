package ar.edu.ues21.pricingbff.exception;

import org.springframework.http.HttpStatus;

/**
 * Base Service Exception
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 8013209203044555406L;
    private final HttpStatus code;

    /**
     * Creates a new instance of {@link ServiceException}
     *
     * @param message the exception message
     */
    public ServiceException(final String message) {
        super(message);
        code = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * Creates a new instance of {@link ServiceException}
     *
     * @param message the exception message
     * @param cause   the exception cause
     */
    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
        code = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * Creates a new instance of {@link ServiceException}
     *
     * @param message the exception message
     * @param code    the {@link HttpStatus} used that will be used in the REST response
     */
    public ServiceException(final String message, final HttpStatus code) {
        super(message);
        this.code = code;
    }

    /**
     * Creates a new instance of {@link ServiceException}
     *
     * @param message the exception message
     * @param cause   the exception cause
     * @param code    the {@link HttpStatus} used that will be used in the REST response
     */
    public ServiceException(final String message, final Throwable cause, final HttpStatus code) {
        super(message, cause);
        this.code = code;
    }

    /**
     * Retrieves the {@link HttpStatus} associated to the exception
     *
     * @return the {@link HttpStatus}
     */
    public HttpStatus getCode() {
        return code;
    }
}
