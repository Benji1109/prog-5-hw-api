package hu.mik.prog5.reactive.controller;


import hu.mik.prog5.reactive.configuration.jwt.JwtUtils;
import hu.mik.prog5.reactive.configuration.jwt.model.JwtResponse;
import hu.mik.prog5.reactive.configuration.jwt.model.LoginRequest;
import hu.mik.prog5.reactive.configuration.jwt.model.MessageResponse;
import hu.mik.prog5.reactive.configuration.jwt.model.SignupRequest;
import hu.mik.prog5.reactive.document.EnumRole;
import hu.mik.prog5.reactive.document.Role;
import hu.mik.prog5.reactive.document.User;
import hu.mik.prog5.reactive.repository.RoleRepository;
import hu.mik.prog5.reactive.repository.UserRepository;
import hu.mik.prog5.reactive.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String accessToken = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(accessToken,
			userDetails.getId(),
			userDetails.getUsername(),
			userDetails.getEmail(),
			roles));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
				.badRequest()
				.body(new MessageResponse("Error: Username is already taken!"));
		}
		
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
				.badRequest()
				.body(new MessageResponse("Error: Email is already in use!"));
		}
		
		User user = new User(
			signUpRequest.getUsername(),
			signUpRequest.getEmail(),
			passwordEncoder.encode(signUpRequest.getPassword())
		);
		
		Set<Role> roles = signUpRequest
			.getRoles()
			.stream()
			.map(EnumRole::getEnumRoleByName)
			.map(role -> roleRepository.findByName(role).orElseThrow(() -> new RuntimeException("Error: Role is not found.")))
			.collect(Collectors.toSet());
		roles.add(roleRepository.findByName(EnumRole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
		roles.add(roleRepository.findByName(EnumRole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
		roles.add(roleRepository.findByName(EnumRole.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
		
		user.setRoles(roles);
		userRepository.save(user);
		
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}