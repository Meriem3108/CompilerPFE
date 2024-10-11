//package com.example.testpfe.Configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Désactiver CSRF
//                .authorizeRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers("/home","/guide", "/traduction","/traductionscenario", "/register", "/login","/registration","/users").permitAll() // Ajout de /register et /login
//                        .requestMatchers("/api/compiler/template/compile").permitAll()
//                        .requestMatchers("/api/compiler/scenario/compile").permitAll()
//                        .requestMatchers("/api/uploadTemplate").permitAll()
//                        .requestMatchers("/api/uploadScenario").permitAll()
//                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
//                        .requestMatchers("/header.html", "/footer.html", "/sidebar.html").permitAll()
//                        .anyRequest().authenticated()
//
//                )
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/login") // Page de connexion personnalisée
//                        .defaultSuccessUrl("/home", true) // Redirection après connexion réussie
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout") // URL de déconnexion
//                        .logoutSuccessUrl("/login?logout") // Redirection après déconnexion
//                        .permitAll()
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(); // Pour le cryptage des mots de passe
//    }
//
//}
package com.example.testpfe.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProviderConfig authenticationProviderConfig;

    public SecurityConfig(AuthenticationProviderConfig authenticationProviderConfig) {
        this.authenticationProviderConfig = authenticationProviderConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/home", "/guide", "/traduction", "/traductionscenario", "/register", "/login", "/registration", "/users").permitAll()
                                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                                .requestMatchers("/header.html", "/footer.html", "/sidebar.html").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProviderConfig.authenticationProvider());
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProviderConfig.authenticationProvider())
                .build();
    }
}
