package Xanadu.Configs;

import jakarta.servlet.RequestDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@Slf4j
public class CustomerSecurityConfiguration {

    @Autowired
    @Qualifier("customerAuthService")
    UserDetailsService customerAuthService;

    @Bean
    @Order(3)
    public SecurityFilterChain customerApiFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .userDetailsService(customerAuthService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/cartItems**")
                        .hasAuthority("USER").anyRequest().permitAll()
                )
                .build();
    }

    @Bean
    @Order(4)
    public SecurityFilterChain customerFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .userDetailsService(customerAuthService)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/cart.html", "/checkout.html")
                                .hasAuthority("USER")
                                .anyRequest().permitAll()
                )
                .formLogin(
                        cf -> cf.loginPage("/signin.html")
                                .loginProcessingUrl("/login")
                                .failureHandler((request, response, authenticationException) -> {
                                    request.setAttribute("message", authenticationException.getMessage());
                                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/signin");
                                    requestDispatcher.forward(request, response);
                                })
                                .defaultSuccessUrl("/home.html", true)
                )
                .logout(
                        cf -> cf
                                .logoutUrl("/logout")
                                .deleteCookies()
                                .invalidateHttpSession(true)
                                .logoutSuccessUrl("/home.html")
                )
                .sessionManagement(
                        ss -> ss
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(false)
                )
                .exceptionHandling((exceptionHandling) -> {
                            exceptionHandling.accessDeniedHandler((request, response, accessDeniedException) -> {
                                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                                log.info("authentication {}",authentication);
                                request.setAttribute("message", accessDeniedException.getMessage());
                                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/signin");
                                requestDispatcher.forward(request, response);
                            });
                        }
                )
                .build();
    }

}
