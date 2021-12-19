package com.estateguru.banktransactions.configs.swagger;

import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ReflectionUtils;
import springfox.documentation.service.*;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.*;

@Configuration
@EnableOpenApi
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .pathMapping("/")
                .enable(true)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.estateguru.banktransactions.controllers"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(List.of(apiKey()))
                .securityContexts(List.of(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("REST API")
                .description("REST API for ABM Shop")
                .version("0.1.0")
                .contact(new Contact("Estate Guru", "", "info@estateguru.co"))
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .operationSelector(operationContext -> true)
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("Authorization", authorizationScopes));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        try {
            Field registrationsField = FieldUtils.getField(InterceptorRegistry.class, "registrations", true);
            List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils.getField(registrationsField, registry);
            if (registrations != null) {
                for (InterceptorRegistration interceptorRegistration : registrations)
                    interceptorRegistration
                            .excludePathPatterns("/swagger**/**")
                            .excludePathPatterns("/webjars/**")
                            .excludePathPatterns("/v3/**")
                            .excludePathPatterns("/doc.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
