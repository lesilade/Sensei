package com.sensei.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;


@ConfigurationProperties("endpoints")
public class ApplicationProperties {
	
	private final CorsConfiguration cors = new CorsConfiguration();
	
	
	 public CorsConfiguration getCors() {
	        return cors;
	    }
	
	
   @Bean
    public CorsFilter getCorsFilter()
    {
    	CorsConfiguration config = new CorsConfiguration();
    	ResourceHttpRequestHandler cors = new ResourceHttpRequestHandler();
    	cors.setCorsConfiguration(config);
    	return new CorsFilter(cors);
    }
    
    
/*   @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = this.getCors();
        if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
            //log.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", config);
            source.registerCorsConfiguration("/v2/api-docs", config);
        }
        return new CorsFilter(source);
    }*/
   
   @Bean
   public WebMvcConfigurer corsConfigurer() {
       return new WebMvcConfigurerAdapter() {
           @Override
           public void addCorsMappings(CorsRegistry registry) {
               registry.addMapping("/api/register").allowedOrigins("http://localhost:8100");
           }
       };
   }
   

}
