package com.nari.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;
import java.security.KeyPair;


/**
 * OAuth2 Server Launcher
 *
 * @author John Zhang
 */
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(WebMvcConfiguration.class)
public class OAuth2ServiceApplication {

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        return jdbcUserDetailsManager;
    }

    @Autowired
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jdbcUserDetailsManager())
            .passwordEncoder(new BCryptPasswordEncoder());
    }

    public static void main(String[] args) {
        SpringApplication.run(OAuth2ServiceApplication.class, args);
    }

    @Configuration
    @Order(-20)
    protected static class LoginConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .formLogin().loginPage("/login").permitAll()
                .and()
                .requestMatchers().antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access")
                .and()
                .authorizeRequests().anyRequest().authenticated();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.parentAuthenticationManager(authenticationManager);
        }
    }

    @Configuration
    @EnableResourceServer
    protected static class ResourceServer extends ResourceServerConfigurerAdapter {

        @Autowired
        private TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources)
                throws Exception {
            resources.tokenStore(tokenStore);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .anyRequest().authenticated();
        }

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class ServerConfig extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager auth;

        @Autowired
        private DataSource dataSource;

        private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        @Bean
        public JdbcTokenStore tokenStore() {
            return new JdbcTokenStore(dataSource);
        }
        @Bean
        protected AuthorizationCodeServices authorizationCodeServices() {
            return new JdbcAuthorizationCodeServices(dataSource);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security)
                throws Exception {
            security.tokenKeyAccess("permitAll()")
                    .checkTokenAccess("isAuthenticated()")
                    .passwordEncoder(passwordEncoder);
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            KeyPair keyPair = new KeyStoreKeyFactory(
                    new ClassPathResource("keystore.jks"), "foobar".toCharArray())
                    .getKeyPair("test");
            converter.setKeyPair(keyPair);
            return converter;
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            endpoints.authorizationCodeServices(authorizationCodeServices())
                     .authenticationManager(auth)
                     .tokenStore(tokenStore())
                     .approvalStoreDisabled()
                     .accessTokenConverter(jwtAccessTokenConverter());
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.jdbc(dataSource)
                   .passwordEncoder(passwordEncoder);
        }

    }

}
