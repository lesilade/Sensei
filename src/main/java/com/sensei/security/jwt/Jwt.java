package com.sensei.security.jwt;

import org.springframework.stereotype.Component;

@Component
public class Jwt {

	 private String secret;

	    private long tokenValidityInSeconds = 1800;

	    private long tokenValidityInSecondsForRememberMe = 2592000;

	    public String getSecret() {
	        return secret;
	    }

	    public void setSecret(String secret) {
	        this.secret = secret;
	    }

	    public long getTokenValidityInSeconds() {
	        return tokenValidityInSeconds;
	    }

	    public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
	        this.tokenValidityInSeconds = tokenValidityInSeconds;
	    }

	    public long getTokenValidityInSecondsForRememberMe() {
	        return tokenValidityInSecondsForRememberMe;
	    }

	    public void setTokenValidityInSecondsForRememberMe(long tokenValidityInSecondsForRememberMe) {
	        this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
	    }
}
