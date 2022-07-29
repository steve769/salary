package info.stephenderrick.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SalaryApplicationSecurityConfig {
        //Note that the WebSecurityConfigurerAdapter class is deprecated
        //So in order to configure spring security we create a bean of SecurityFilterChain

        private final String [] WHITE_LISTED_URLS = {
                "api/user/**",
                "/api/auth/**"
        };
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
                http
                        .csrf().disable()
                        .authorizeRequests()
                        .antMatchers(WHITE_LISTED_URLS)
                        .permitAll();
                return http.build();
        }





}
