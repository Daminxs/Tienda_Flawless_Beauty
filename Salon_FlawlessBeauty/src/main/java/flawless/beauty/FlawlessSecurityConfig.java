/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty;

/**
 *
 * Encargado para: Damian
 */

import flawless.beauty.service.FlawlessUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class FlawlessSecurityConfig {

    private final FlawlessUserDetailsService userDetailsService;

    public FlawlessSecurityConfig(
            FlawlessUserDetailsService userDetailsService) {

        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider auth =
                new DaoAuthenticationProvider();

        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());

        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http
            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    "/", "/index",
                    "/cuenta",
                    "/login",
                    "/registro",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/img/**"
                ).permitAll()

                .requestMatchers("/admin/**")
                    .hasRole("ADMIN")

                .requestMatchers(
                        "/perfil",
                        "/editarCuenta",
                        "/eliminarCuenta",
                        "/verReservas",
                        "/verCitas"
                ).authenticated()

                .anyRequest().permitAll()
            )

            .formLogin(login -> login
                .loginPage("/login")
                .usernameParameter("correo")
                .passwordParameter("password")
                .defaultSuccessUrl("/perfil")
                .failureUrl("/login?error=true")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/cerrarSesion")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )

            .exceptionHandling(ex -> ex
                .accessDeniedPage("/login")
            );

        return http.build();
    }
}

