package com.sensei.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("sensei")
public class EmailConfigurations 
{
 
	private final Mail mail = new Mail();
	
    public Mail getMail() {
        return mail;
    }
    
	public static class Mail {

        private String from = "";

        private String baseUrl = "";

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }
	
}


