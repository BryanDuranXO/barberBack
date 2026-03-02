package com.example.barber.security;



import com.example.barber.security.jwt.JwtAuthenticationFilter;
import com.example.barber.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class MainSecurity {

    private final String[] WHITE_LIST = {
            "/api/auth/**",


};

    private final UserDetailsService userDetailsService;

    public MainSecurity(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dao =
                new DaoAuthenticationProvider(userDetailsService);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter filter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST).permitAll()
                                .requestMatchers("/api/barber/citas/").permitAll()
                                .requestMatchers("/api/barber/citas/fecha/**").hasAnyRole("ADMIN")
                                .requestMatchers("/api/barber/citas/status/false").hasAnyRole("ADMIN")
                                .requestMatchers("/api/barber/cita/status/true").hasAnyRole("ADMIN")
                                .requestMatchers("/api/barber/usuarios/rol/**").hasAnyRole("ADMIN")
                                .requestMatchers("/api/servicios/").hasAnyRole("ADMIN")
                                .requestMatchers("/api/barber/usuarios/save").hasAnyRole("ADMIN")
                                .requestMatchers("/api/barber/usuarios/").hasAnyRole("ADMIN")
                                .requestMatchers("/api/barber/citas/scan-cita").hasAnyRole("ADMIN", "BARBER")
                                .requestMatchers("/api/barber/usuarios/new-password").hasAnyRole("ADMIN", "BARBER")
                                .requestMatchers("/api/barber/usuarios/edit/**").hasAnyRole("ADMIN", "BARBER")
                                .requestMatchers("/api/barber/citas/eliminar/**").hasAnyRole("ADMIN")
                                .requestMatchers("/api/barber/usuarios/eliminar/**").hasAnyRole("ADMIN")

                                .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}