
package com.hungdev.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

	@Value("${jwt.secret}")
	private String secretKey;

	private Long expirationTime = 24 * 60 * 60 * 1000l;

	public String getSecretKey() {
		return secretKey;
	}

	public Long getExpirationTime() {
		return expirationTime;
	}
}
