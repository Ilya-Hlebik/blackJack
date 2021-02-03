package com.blackJack.configuration;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
@PropertySources({@PropertySource("classpath:mail.properties")})
public class MainConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
    @Bean
    public List<String> excludePatterns() {
        return List.of("/swagger-resources/**", "/swagger-ui.html", "/configuration/**", "/webjars/**", "/public",
                "/v2/api-docs", "/users/signin", "/users/signup", "/users/update_pass", "/mail/forgot_password/**",
                "/mail/verify_token", "/users/update_pass/without_previous", "/product/all",
                "/storage/**");
    }

}
