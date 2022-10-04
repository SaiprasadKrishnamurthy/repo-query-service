package com.informatica.repoquery.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.util.DefaultUriBuilderFactory;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.Duration;
import java.time.LocalDate;

/**
 * Application configuration.
 *
 * @author Sai.
 */
@EnableSwagger2
@EnableWebMvc
@Configuration
public class RepoQueryServiceAppConfig {

    @Qualifier("githubRestTemplate")
    @Bean
    public RestTemplate githubRestTemplate(
            @Value("${github.api.base.url}") final String githubApiBaseUrl,
            @Value("${github.api.connect.timeout.seconds}") final int gitHubApiConnectTimeoutSeconds,
            @Value("${github.api.read.timeout.seconds}") final int gitHubApiReadTimeoutSeconds) {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(githubApiBaseUrl);
        final RestTemplate restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(gitHubApiConnectTimeoutSeconds))
                .setReadTimeout(Duration.ofSeconds(gitHubApiReadTimeoutSeconds))
                .build();
        restTemplate.setUriTemplateHandler(uriBuilderFactory);
        return restTemplate;
    }

    @Bean
    public Docket api(final Environment environment) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.informatica.repoquery"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo(environment))
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo(final Environment environment) {
        return new ApiInfoBuilder()
                .title("REPO QUERY SERVICE REST APIs")
                .contact(
                        new Contact(
                                "Sai",
                                "https://github.com/SaiprasadKrishnamurthy",
                                "saiprasad.krishnamurthy@gmail.com"
                        )
                )
                .version("Build: " + environment.getProperty("build.version"))
                .build();
    }
}
