package hu.mik.prog5.reactive.configuration.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	
	private String accessToken;
	private String id;
	private String username;
	private String email;
	private List<String> roles;
	
}
