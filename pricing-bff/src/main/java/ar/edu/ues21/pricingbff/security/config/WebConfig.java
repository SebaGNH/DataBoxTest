package ar.edu.ues21.pricingbff.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
    InstanceNameInterceptor instanceNameInterceptor;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(instanceNameInterceptor);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedHeaders("*");
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addRedirectViewController("/v1/api-docs", "/v1/api-docs");
        registry.addRedirectViewController("/swagger-resources/configuration/ui",
            "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/swagger-resources/configuration/security",
            "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/swagger-resources", "/swagger-resources");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}