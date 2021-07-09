package lu.ftn.bank2service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();}

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        CorsConfiguration defaultConfiguration = new CorsConfiguration();
        defaultConfiguration.applyPermitDefaultValues();

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS",  "HEAD", "TRACE", "CONNECT"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        configuration = configuration.combine(defaultConfiguration);
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable().
                sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
                authorizeRequests().
                antMatchers("/h2-console/**").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll().
                antMatchers("/api/auth").permitAll().
                antMatchers(HttpMethod.PUT, "/api/payment").permitAll().
                antMatchers(HttpMethod.PUT, "/api/payment/**").permitAll().
                antMatchers(HttpMethod.POST, "/api/invoice").permitAll().
                antMatchers(HttpMethod.POST, "/api/invoice/**").permitAll().
                anyRequest().authenticated().and().cors().configurationSource(corsConfigurationSource());
    }
}
