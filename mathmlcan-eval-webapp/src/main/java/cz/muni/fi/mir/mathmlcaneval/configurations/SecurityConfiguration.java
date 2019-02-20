package cz.muni.fi.mir.mathmlcaneval.configurations;

import cz.muni.fi.mir.mathmlcaneval.configurations.props.OAuthProperties;
import cz.muni.fi.mir.mathmlcaneval.filters.CorsFilter;
import cz.muni.fi.mir.mathmlcaneval.filters.TraceFilter;
import cz.muni.fi.mir.mathmlcaneval.security.OAuthSecurityKey;
import cz.muni.fi.mir.mathmlcaneval.security.SecurityConstants;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
public class SecurityConfiguration {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }

  @Configuration
  @RequiredArgsConstructor
  public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth
        .userDetailsService(this.userDetailsService)
        .passwordEncoder(this.passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      // @formatter:off
      http
        .authorizeRequests()
          .antMatchers(HttpMethod.POST, SecurityConstants.POST_PUBLIC_RESOURCES).permitAll()
          .antMatchers(HttpMethod.GET, SecurityConstants.GET_PUBLIC_RESOURCES).permitAll()
          .antMatchers(HttpMethod.PUT, SecurityConstants.PUT_PUBLIC_RESOURCES).permitAll()
          .antMatchers(HttpMethod.PATCH, SecurityConstants.PATCH_PUBLIC_RESOURCES).permitAll()
          .anyRequest()
            .authenticated()
        .and()
          .csrf()
            .disable();
      // @formatter:on
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
    }
  }

  @Configuration
  @EnableAuthorizationServer
  @RequiredArgsConstructor
  public static class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final OAuthSecurityKey oAuthSecurityKey;
    private final PasswordEncoder passwordEncoder;
    private final DataSource dataSource;
    private final OAuthProperties oAuthProperties;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      clients
        .jdbc(this.dataSource)
        .passwordEncoder(this.passwordEncoder);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      endpoints
        .tokenServices(defaultTokenServices())
        .authenticationManager(this.authenticationManager)
        .userDetailsService(this.userDetailsService)
        .pathMapping("/oauth/token", "/api/oauth/token");
    }

    @Bean
    public DefaultTokenServices defaultTokenServices() {
      DefaultTokenServices tokenServices = new DefaultTokenServices();
      tokenServices.setTokenStore(new JdbcTokenStore(this.dataSource));
      tokenServices.setTokenEnhancer(jwtAccessTokenConverter());
      tokenServices.setSupportRefreshToken(true);
      tokenServices.setAccessTokenValiditySeconds(oAuthProperties.getTokenValidity());

      return tokenServices;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
      JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
      jwtAccessTokenConverter.setKeyPair(oAuthSecurityKey.getKey());

      return jwtAccessTokenConverter;
    }
  }

  @Configuration
  @EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
  public static class MethodSecurity extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
      return new OAuth2MethodSecurityExpressionHandler();
    }
  }

  @Configuration
  @EnableResourceServer
  @RequiredArgsConstructor
  public static class Oauth2ResourceConfig extends ResourceServerConfigurerAdapter {
    private final TraceFilter traceFilter;
    private final CorsFilter corsFilter;
    private final ResourceServerTokenServices resourceServerTokenServices;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
      resources
        .tokenServices(resourceServerTokenServices)
        .resourceId(SecurityConstants.RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
      // @formatter:off
      http
        .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.NEVER)
        .and()
          .authorizeRequests()
          .antMatchers(HttpMethod.POST, SecurityConstants.POST_PUBLIC_RESOURCES).permitAll()
          .antMatchers(HttpMethod.GET, SecurityConstants.GET_PUBLIC_RESOURCES).permitAll()
          .antMatchers(HttpMethod.PUT, SecurityConstants.PUT_PUBLIC_RESOURCES).permitAll()
          .antMatchers(HttpMethod.PATCH, SecurityConstants.PATCH_PUBLIC_RESOURCES).permitAll()
          .anyRequest()
            .authenticated()
        .and()
          .headers()
            .cacheControl()
              .disable()
        .and()
          .csrf()
            .disable()
        .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
        .addFilterBefore(traceFilter, CorsFilter.class);
      // @formatter:on
    }
  }
}
