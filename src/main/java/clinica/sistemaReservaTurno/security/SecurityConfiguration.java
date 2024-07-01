package clinica.sistemaReservaTurno.security;

import clinica.sistemaReservaTurno.service.UsuarioDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UsuarioDetalleService usuarioDetalleService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/register/**", "/h2-console/**","/odontologos/**", "/pacientes/**","/turnos/**", "/registro.html", "/js/**","/swagger-ui/**").permitAll();
                    registry.requestMatchers("/home_admin.html", "/get_pacientes.html", "/post_pacientes.html", "/get_odontologos.html", "/post_odontologos.html").hasRole("ADMIN");
                    registry.requestMatchers("/home_user.html","/get_turnos.html", "/post_turnos.html").hasRole("USER");
                    registry.anyRequest().authenticated();
                })
                //.formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer
                            .successHandler(new AuthenticationSucessHandler())
                            .permitAll();
                })
                .build();
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        UserDetails normalUser = User.builder()
                .username("gc")
                .password("$2a$12$SL5xcRJZjuX4PFvDyRjgaevH1ryNWwzVU8oSFZRnWrWrok0HX647.")
                .roles("USER")
                .build();
        UserDetails adminUser = User.builder()
                .username("admin")
                .password("$2a$12$MAdutSBNA6SGgkUBCcZdYe7oJdF/Tk3Ngh.vxYVyC1sA1Uu0/fRae")
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(normalUser, adminUser);
    }*/

    @Bean
    public UserDetailsService userDetailsService() {
        return usuarioDetalleService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioDetalleService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
