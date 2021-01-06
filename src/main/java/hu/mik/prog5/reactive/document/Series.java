package hu.mik.prog5.reactive.document;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;

@Data
public class Series {
	
	@Id
	private String id;
	@NotBlank
	private String title;
	private String author;
	private Integer productionTime;
	private EnumRate rate;
	private Boolean published;
	
}
