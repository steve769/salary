package info.stephenderrick.security.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
public class SalaryApplicationSecurityConfig {
        //Note that the WebSecurityConfigurerAdapter class is deprecated
        //So in order to configure spring security we create a bean of SecurityFilterChain
        private final UserDetailsService userDetailsService;
        private final PasswordEncoder passwordEncoder;
        @Value("${jwt.public.key}")
        RSAPublicKey publicKey;
        @Value("${jwt.private.key}")
        RSAPrivateKey privateKey;
        private final String [] WHITE_LISTED_URLS = {
                "api/user/**",
                "/api/auth/**"
        };

        public SalaryApplicationSecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
                this.userDetailsService = userDetailsService;
                this.passwordEncoder = passwordEncoder;
        }
        //This method creates the authentication manager bean for us tom use
        @Bean
        public AuthenticationManager authenticationManager
                (AuthenticationConfiguration authenticationConfiguration) throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
                http
                        .csrf().disable()
                        .authorizeRequests()
                        .antMatchers(WHITE_LISTED_URLS)
                        .permitAll();
                return http.build();
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
             authenticationManagerBuilder.userDetailsService(userDetailsService)
                     .passwordEncoder(passwordEncoder);
        }

        @Bean
        JwtDecoder jwtDecoder() {
                return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
        }

        @Bean
        JwtEncoder jwtEncoder() {
                JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
                JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
                return new NimbusJwtEncoder(jwks);
        }


}
