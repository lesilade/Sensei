package com.sensei;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import com.sensei.config.ApplicationProperties;
import com.sensei.config.DefaultProfileUtil;
import com.sensei.config.EmailConfigurations;
import com.sensei.config.SenseiProperties;

//@ComponentScan
//@EnableAutoConfiguration(exclude = {MetricFilterAutoConfiguration.class, MetricRepositoryAutoConfiguration.class})


//@ComponentScan
//@EnableAutoConfiguration(exclude = {MetricFilterAutoConfiguration.class, MetricRepositoryAutoConfiguration.class})
//@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
@EnableConfigurationProperties({SenseiProperties.class, ApplicationProperties.class, EmailConfigurations.class})
@SpringBootApplication
//@EntityScan(basePackages = {"com.sensei.domain"})
public class SenseiApplication {
	
	private static final Logger log = LoggerFactory.getLogger(SenseiApplication.class);

    private final Environment env;

    public SenseiApplication(Environment env) {
        this.env = env;
    }
    
    
    
/*	public static void main(String[] args) {
		SpringApplication.run(SenseiApplication.class, args);
	}*/
    
    public static void main(String[] args) throws UnknownHostException {
	 SpringApplication app = new SpringApplication(SenseiApplication.class);
     DefaultProfileUtil.addDefaultProfile(app);
     Environment env = app.run(args).getEnvironment();
     String protocol = "http";
     if (env.getProperty("server.ssl.key-store") != null) {
         protocol = "https";
     }
     log.info("\n----------------------------------------------------------\n\t" +
             "Application '{}' is running! Access URLs:\n\t" +
             "Local: \t\t{}://localhost:{}\n\t" +
             "External: \t{}://{}:{}\n\t" +
             "Profile(s): \t{}\n----------------------------------------------------------",
         env.getProperty("spring.application.name"),
         protocol,
         env.getProperty("server.port"),
         protocol,
         InetAddress.getLocalHost().getHostAddress(),
         env.getProperty("server.port"),
         env.getActiveProfiles());
	
  }
    
}
