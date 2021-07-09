package lu.ftn.configuration;

import lu.ftn.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }

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

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();}

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilterBean() throws Exception {
        JwtAuthenticationFilter authenticationTokenFilter = new JwtAuthenticationFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO configure properly
        http.
                csrf().disable().
                sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
                authorizeRequests().
                antMatchers("/h2-console/**").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll().
                antMatchers("/api/auth").permitAll().
                antMatchers(HttpMethod.GET, "/api/files").permitAll().
                antMatchers(HttpMethod.GET, "/api/files/**").permitAll().
                antMatchers(HttpMethod.GET, "/api/tasks").permitAll().
                antMatchers(HttpMethod.GET, "/api/tasks/**").permitAll().
                antMatchers(HttpMethod.POST, "/api/tasks/**").permitAll().
                antMatchers(HttpMethod.GET, "/api/process").permitAll().
                antMatchers(HttpMethod.GET, "/api/process/**").permitAll().
                antMatchers(HttpMethod.POST, "/api/plagiarism").permitAll().
                antMatchers(HttpMethod.POST, "/api/plagiarism/**").permitAll().
                antMatchers(HttpMethod.GET, "/api/users").permitAll().
                antMatchers(HttpMethod.GET, "/api/users/**").permitAll().
                antMatchers(HttpMethod.POST, "/api/users/**").permitAll().
                antMatchers(HttpMethod.GET, "/api/forms/users").permitAll().
                antMatchers(HttpMethod.GET, "/api/forms/users/**").permitAll().

                anyRequest().authenticated()
                .and().cors().configurationSource(corsConfigurationSource());
        http.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }
}

