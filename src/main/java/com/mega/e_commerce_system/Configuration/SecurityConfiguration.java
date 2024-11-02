package com.mega.e_commerce_system.Configuration;

import com.mega.e_commerce_system.Modules.customer.Entities.Permission;
import com.mega.e_commerce_system.Secutiry.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    private static final String[] WHITE_LIST_URL =
            {"/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
            };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers(WHITE_LIST_URL).permitAll()

                        // Customer Endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers/**").hasAuthority(Permission.CUSTOMER_VIEW.getPermission())
                        .requestMatchers(HttpMethod.POST, "/api/v1/customers/**").hasAuthority(Permission.CUSTOMER_CREATE.getPermission())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/customers/**").hasAuthority(Permission.CUSTOMER_UPDATE.getPermission())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/customers/**").hasAuthority(Permission.CUSTOMER_DELETE.getPermission())

                        // Product Endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/**").hasAuthority(Permission.PRODUCT_VIEW.getPermission())
                        .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasAuthority(Permission.PRODUCT_CREATE.getPermission())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasAuthority(Permission.PRODUCT_UPDATE.getPermission())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasAuthority(Permission.PRODUCT_DELETE.getPermission())


                        // Order Endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders/**").hasAuthority(Permission.ORDER_VIEW.getPermission())
                        .requestMatchers(HttpMethod.GET, "/api/v1/order-line/**").hasAuthority(Permission.ORDER_LINE_VIEW.getPermission())
                        .requestMatchers(HttpMethod.POST, "/api/v1/orders/**").hasAuthority(Permission.ORDER_CREATE.getPermission())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/orders/**").hasAuthority(Permission.ORDER_UPDATE.getPermission())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/**").hasAuthority(Permission.ORDER_DELETE.getPermission())

                        // Payment Endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/payments/**").hasAuthority(Permission.PAYMENT_VIEW.getPermission())
                        .requestMatchers(HttpMethod.POST, "/api/v1/payments/**").hasAuthority(Permission.PAYMENT_CREATE.getPermission())

                        // Address Endpoints
                        .requestMatchers(HttpMethod.GET, "/api/v1/address/**").hasAuthority(Permission.ADDRESS_VIEW.getPermission())
                        .requestMatchers(HttpMethod.POST, "/api/v1/address/**").hasAuthority(Permission.ADDRESS_CREATE.getPermission())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/address/**").hasAuthority(Permission.ADDRESS_UPDATE.getPermission())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/address/**").hasAuthority(Permission.ADDRESS_DELETE.getPermission())


                        // Restrict all other requests
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                 ;

        return http.build();
    }
}
