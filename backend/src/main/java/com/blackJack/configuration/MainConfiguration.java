package com.blackJack.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.util.List;

@Configuration
@PropertySources({@PropertySource("mail.properties")})
public class MainConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public List<String> excludePatterns() {
        return List.of("/swagger-resources/**", "/swagger-ui.html", "/configuration/**", "/webjars/**", "/public",
                "/v2/api-docs", "/users/signin", "/users/signup", "/users/update_pass", "/mail/forgot_password/**",
                "/mail/verify_token", "/users/update_pass/without_previous", "/product/all",
                "/storage/**");
    }

}
