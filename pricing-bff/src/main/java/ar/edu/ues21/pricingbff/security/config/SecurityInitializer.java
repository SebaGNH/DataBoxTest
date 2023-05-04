package ar.edu.ues21.pricingbff.security.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

// tag::class[]
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityInitializer() {
        super(SecurityConfig.class, HazelcastHttpSessionConfig.class);
    }
}