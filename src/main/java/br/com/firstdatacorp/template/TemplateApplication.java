package br.com.firstdatacorp.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication(scanBasePackages = { "br.com.firstdatacorp.template.*" })
public class TemplateApplication extends SpringBootServletInitializer {
	
	 @Override
     protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
         return application.sources(TemplateApplication.class);
     }

    public static void main(String[] args) {
        SpringApplication.run(TemplateApplication.class, args);
    }
    
    @Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propsConfig = new PropertySourcesPlaceholderConfigurer();
        propsConfig.setLocation(new ClassPathResource("git.properties"));
        propsConfig.setIgnoreResourceNotFound(true);
        propsConfig.setIgnoreUnresolvablePlaceholders(true);
        return propsConfig;
    }
}