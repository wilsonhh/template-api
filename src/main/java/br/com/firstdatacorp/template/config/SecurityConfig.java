package br.com.firstdatacorp.template.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.firstdatacorp.template.filter.AuthorizationFilter;
import br.com.firstdatacorp.template.properties.CorsProperties;
import br.com.firstdatacorp.template.security.FDSecurity;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private FDSecurity fdSecurity;
	
	private final CorsProperties corsProperties;
	
	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver resolver;

	public SecurityConfig(CorsProperties corsProperties) {
		this.corsProperties = corsProperties;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().contentSecurityPolicy(getCSP());
		http
			.csrf().disable()
			.authorizeRequests()
			.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
			.mvcMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html**", "/webjars/**").permitAll()
			.mvcMatchers("/token/**").anonymous()
			.mvcMatchers("/actuator/**").permitAll()
			.mvcMatchers("/template/**").permitAll()
			.mvcMatchers("/isAlive**").permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilterBefore(new AuthorizationFilter(fdSecurity, resolver), UsernamePasswordAuthenticationFilter.class)
		;
	}
	
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().mvcMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html**", "/webjars/**", "/isAlive**");
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
					.addMapping("/**")
					.allowedMethods(corsProperties.getAllowedMethods())
					.allowedHeaders(corsProperties.getExposedHeaders())
					.exposedHeaders(corsProperties.getExposedHeaders())
					.allowedOrigins(corsProperties.getAllowedOrigins());
			}
		};
	}
	
	private String getCSP() {
		return "default-src 'self'";
	}
	
}
