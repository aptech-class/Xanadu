package Xanadu.Configs;

import Xanadu.Entities.Role;
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
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@Slf4j
public class AdminSecurityConfiguration {
    @Autowired
    @Qualifier("userAuthService")
    UserDetailsService userAuthService;

    @Bean
    @Order(1)
    public SecurityFilterChain adminApiFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/admin/api/**")
                .userDetailsService(userAuthService)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
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
                                .requestMatchers("/admin/assets/**", "/admin/signin**").permitAll()
                                .requestMatchers("/admin/**")
                                .hasAuthority(Role.ADMIN.name())
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(
                        cf -> cf
                                .loginPage("/admin/signin.html").permitAll()
                                .loginProcessingUrl("/admin/login").permitAll()
                                .failureHandler((request, response, authenticationException) -> {
                                    request.setAttribute("message", authenticationException.getMessage());
                                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/signin");
                                    requestDispatcher.forward(request, response);
                                })
                                .defaultSuccessUrl("/admin/home.html",true)
                )
                .logout(
                        cf -> cf
                                .logoutUrl("/admin/logout")
                                .deleteCookies()
                                .invalidateHttpSession(true)
                                .logoutSuccessUrl("/admin/signin.html")
                )
                .sessionManagement(
                        ss -> ss
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(false)
                ).exceptionHandling((exceptionHandling) -> {
                            exceptionHandling.accessDeniedHandler((request, response, accessDeniedException) -> {
                                request.setAttribute("message", accessDeniedException.getMessage());
                                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/signin");
                                requestDispatcher.forward(request, response);
                            });
                        }
                )
                .build();
    }


}
