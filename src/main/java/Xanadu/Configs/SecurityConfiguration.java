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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration {
    @Autowired
    @Qualifier("userAuthService")
    UserDetailsService userAuthService;

    @Autowired
    @Qualifier("customerAuthService")
    UserDetailsService customerAuthService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOrigin("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    @Order(1)
    public SecurityFilterChain adminApiFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/admin/api/**")
                .userDetailsService(userAuthService)
                .authorizeHttpRequests(auth->auth.anyRequest().authenticated())
                .build();
    }
    @Bean
    @Order(2)
    public SecurityFilterChain adminFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher("/admin/**")
                .userDetailsService(userAuthService)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
//                                .requestMatchers("/admin/assets/**").permitAll()
//                                .requestMatchers("/admin/**").authenticated()
                                .anyRequest().permitAll()
                )
                .formLogin(cf -> cf.loginPage("/admin/login.html").permitAll())
                .build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain customerApiFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .userDetailsService(customerAuthService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/cartItems**").authenticated()
                        .anyRequest().permitAll()
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
                                .requestMatchers("/cart.html","/checkout.html").authenticated()
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
                                .defaultSuccessUrl("/home.html",true)
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
                .build();
    }

}
