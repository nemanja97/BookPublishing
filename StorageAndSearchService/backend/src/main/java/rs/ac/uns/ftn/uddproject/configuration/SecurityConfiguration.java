package rs.ac.uns.ftn.uddproject.configuration;

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
import rs.ac.uns.ftn.uddproject.security.JwtAuthenticationFilter;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Qualifier("userDetailsServiceImpl")
  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder)
      throws Exception {
    authenticationManagerBuilder
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

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

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    CorsConfiguration defaultConfiguration = new CorsConfiguration();
    defaultConfiguration.applyPermitDefaultValues();

    configuration.setAllowedMethods(
        Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    configuration = configuration.combine(defaultConfiguration);
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/h2-console/**")
        .permitAll()
        .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
        .permitAll()
        .antMatchers("/api/auth")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/api/search")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/api/search/**")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/api/books")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/api/books/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .cors()
        .configurationSource(corsConfigurationSource());

    http.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);
  }
}
