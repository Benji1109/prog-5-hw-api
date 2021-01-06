package hu.mik.prog5.reactive.document;

import java.util.Arrays;

public enum EnumRole {
	
	ROLE_USER("user"), ROLE_MODERATOR("moderator"), ROLE_ADMIN("admin");
	
	private String name;
	
	EnumRole(String name) {
		this.name = name;
	}
	
	public static EnumRole getEnumRoleByName(String name) {
		return Arrays.stream(values())
			.filter(role -> role.name.equals(name))
			.findFirst()
			.orElse(EnumRole.ROLE_USER);
	}
	
}
