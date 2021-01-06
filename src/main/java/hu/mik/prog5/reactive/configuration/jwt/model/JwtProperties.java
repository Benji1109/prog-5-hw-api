package hu.mik.prog5.reactive.configuration.jwt.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
	
	private String jwtSecret;
	private int jwtExpirationMs;
	
}
