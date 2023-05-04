package ar.edu.ues21.pricingbff.client;

import ar.edu.ues21.oauth.retrofit.autoconfigure.auth.AuthInterceptorFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

@Configuration
public class ClientBuilder {
	
	private static final String PRICING_SERVICE = "pricing_API";
    private static final String PRICING_READ = "pricing:read";
    private static final String PRICING_WRITE = "pricing:write";
           
    @Value("${pricing-ues21-bff-dev.pricing-ues21-backend.base-url}")
    private String pricingBaseUrl;
  
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthInterceptorFactory authInterceptorFactory;
    
    
    @Bean
    public PricingClient pricingClient(){
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        final OkHttpClient.Builder builder = new OkHttpClient.Builder().readTimeout(300, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptorFactory.newBuilder().withServiceName(PRICING_SERVICE)
                        .withScope(PRICING_READ).withScope(PRICING_WRITE).build());
        final OkHttpClient client = builder.build();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(pricingBaseUrl)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        return retrofit.create(PricingClient.class);
    }
    
   
}
