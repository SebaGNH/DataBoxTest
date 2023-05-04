package ar.edu.ues21.pricing.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

   /**
    * Handle generic service exception
    *
    * @param ex the {@link ServiceException} exception
    * @return the {@link ServiceException} with the error information
    */
   @ExceptionHandler(ServiceException.class)
   public final ResponseEntity<ServiceException> handleServiceException(final ServiceException ex) {

      return new ResponseEntity<>(ex, ex.getCode());
   }

}
    
    