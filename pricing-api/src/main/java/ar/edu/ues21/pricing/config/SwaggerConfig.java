package ar.edu.ues21.pricing.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${pricing-ues21-backend-dev.oauth.server}")
    private String baseAuthUrl;

    @Bean
    public OpenAPI springShopOpenAPI() {
        final String securitySchemeName = "oauth2";
        return new OpenAPI()
              .info(new Info()
                    .title("Pricing API")
                    .description("Pricing api ")
                    .version("v1.1"))
              .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
              .components(new Components().addSecuritySchemes(securitySchemeName, getSecurityScheme()));
    }

    private SecurityScheme getSecurityScheme() {
        return new SecurityScheme()
              .type(SecurityScheme.Type.OAUTH2)
              .flows(new OAuthFlows().clientCredentials(new OAuthFlow()
                    .tokenUrl(baseAuthUrl + "/menu/api/oauth2/token")
                    .scopes(new Scopes().addString("pricing:read", "pricing read")
                                        .addString("pricing:write", "pricing write"))));
    }

}
