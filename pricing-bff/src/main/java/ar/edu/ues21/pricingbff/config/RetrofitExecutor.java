package ar.edu.ues21.pricingbff.config;

import ar.edu.ues21.pricingbff.exception.ProxyException;
import ar.edu.ues21.pricingbff.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * Utility class to execute retrofit calls
 */
@Component
public class RetrofitExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetrofitExecutor.class);

    public <R> R execute(Call<R> callable) {
        try {
        	final Response<R> result = callable.execute();
            if (!result.isSuccessful()) {
                throw new ProxyException(result);
            }
            return result.body();
        } catch (IOException e) {
            LOGGER.error("execute; Error calling service", e.getMessage());
            throw new ServiceException("Error calling service", e);
        }
    }

}
