package com.buyern.authentication.configs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EqualsAndHashCode(callSuper = true)
@Configuration
@EnableWebSecurity
@Data
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);
    final CustomCorsConfigurationSource corsConfigurationSource;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/helper/**", "/user/auth/**", "/swagger-ui.html", "swagger-ui/**", "/api-docs").permitAll()
                .anyRequest().permitAll()
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).disable();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("/**");
//        config.addAllowedOriginPattern("/**");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}