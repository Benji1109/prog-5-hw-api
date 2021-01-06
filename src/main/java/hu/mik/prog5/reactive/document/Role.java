package hu.mik.prog5.reactive.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
	
	@Id
	private String id;
	private EnumRole name;
	
	public Role(EnumRole name) {
		this.name = name;
	}
	
}
