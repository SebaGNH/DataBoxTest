package ar.edu.ues21.pricingbff.security.config;

import com.google.common.collect.ImmutableList;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2SsoProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerTokenServicesConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.web.configuration.OAuth2ClientConfiguration;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@AutoConfigureBefore({OAuth2AutoConfiguration.class})
@EnableConfigurationProperties(OAuth2SsoProperties.class)
@Import({OAuth2ClientConfiguration.class, ResourceServerTokenServicesConfiguration.class})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Bean
    RequestCache requestCache() {
        return new HttpSessionRequestCache();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("*"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        final SsoSecurityConfigurer sso = new SsoSecurityConfigurer(this.getApplicationContext());
        sso.configure(http);
        http.exceptionHandling().authenticationEntryPoint(new AlwaysSendUnauthorized401AuthenticationEntryPoint());
        http.cors();
        http.headers()
                .frameOptions()
                .disable()
                .addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","script-src 'self'"));
        http.csrf().disable()
		        .authorizeRequests().antMatchers("/v1/**").authenticated()
		        .and().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		        .and().authorizeRequests().antMatchers("/**").permitAll()
		        .and().formLogin()
		        .and().logout().addLogoutHandler((req, res, auth) -> ((OAuth2Authentication) auth).eraseCredentials())
		        .logoutSuccessHandler((req, res, auth) -> res.sendRedirect("/"))
                ;
    }

    private class AlwaysSendUnauthorized401AuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public final void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

}
