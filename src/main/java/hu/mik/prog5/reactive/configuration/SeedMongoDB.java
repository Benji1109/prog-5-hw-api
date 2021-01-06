package hu.mik.prog5.reactive.configuration;

import hu.mik.prog5.reactive.document.EnumRole;
import hu.mik.prog5.reactive.document.Role;
import hu.mik.prog5.reactive.document.User;
import hu.mik.prog5.reactive.repository.RoleRepository;
import hu.mik.prog5.reactive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SeedMongoDB {
	
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Bean
	public void seedDB() {
		List<Role> roles = this.roleRepository.saveAll(Arrays.asList(
			new Role(EnumRole.ROLE_ADMIN),
			new Role(EnumRole.ROLE_MODERATOR),
			new Role(EnumRole.ROLE_USER)
		));
		
		User admin = new User(
			"admin",
			"admin@admin.hu",
			passwordEncoder.encode("12345678")
		);
		admin.setRoles(new HashSet<>(roles));
		
		User moderator = new User(
			"moderator",
			"moderator@moderator.hu",
			passwordEncoder.encode("12345678")
		);
		Set<Role> modRoles = roles.stream().filter(role -> !role.getName().equals(EnumRole.ROLE_ADMIN)).collect(Collectors.toSet());
		moderator.setRoles(modRoles);
		
		User user = new User(
			"user",
			"user@user.hu",
			passwordEncoder.encode("12345678")
		);
		user.setRoles(roles.stream().filter(role -> role.getName().equals(EnumRole.ROLE_USER)).collect(Collectors.toSet()));
		
		this.userRepository.saveAll(Arrays.asList(admin, moderator, user));
	}
}
