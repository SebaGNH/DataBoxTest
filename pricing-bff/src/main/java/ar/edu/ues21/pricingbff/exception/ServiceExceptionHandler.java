package ar.edu.ues21.pricingbff.exception;


import ar.edu.ues21.pricingbff.model.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle not found exceptions
     *
     * @param ex      the {@link NotFoundException} exception
     * @param request the {@link WebRequest}
     * @return the {@link ErrorDetails} with the error information
     */
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleNotFoundException(final NotFoundException ex,
                                                                      final WebRequest request) {
        final ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setCode(HttpStatus.NOT_FOUND.value());
        errorDetails.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    /**
     * Handle generic service exception
     *
     * @param ex      the {@link ServiceException} exception
     * @param request the {@link WebRequest}
     * @return the {@link ErrorDetails} with the error information
     */
    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<ErrorDetails> handleServiceException(final ServiceException ex,
                                                                     final WebRequest request) {
        final ErrorDetails errorDetails = new ErrorDetails();

        errorDetails.setCode(ex.getCode().value());
        errorDetails.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorDetails, ex.getCode());
    }

    /**
     * Handle proxy  exception
     *
     * @param ex      the {@link ProxyException} exception
     * @param request the {@link WebRequest}
     * @return the downstream response body (proxy pass)
     */
    @ExceptionHandler(ProxyException.class)
    public final ResponseEntity<String> handleProxyException(final ProxyException ex,
                                                             final WebRequest request) {

        return new ResponseEntity<String>(ex.getBody(), ex.getCode());
    }
    
      
}
    
    