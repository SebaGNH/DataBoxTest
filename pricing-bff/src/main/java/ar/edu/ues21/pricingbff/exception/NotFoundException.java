package ar.edu.ues21.pricingbff.exception;

/**
 * Base Service Exception
 */
public class NotFoundException extends ServiceException {

    private static final long serialVersionUID = 3069169610903813544L;

    /**
     * Creates a new instance of {@link NotFoundException}
     *
     * @param message the exception message
     */
    public NotFoundException(final String message) {
        super(message);
    }

    /**
     * Creates a new instance of {@link NotFoundException}
     *
     * @param message the exception message
     * @param cause   the exception cause
     */
    public NotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
