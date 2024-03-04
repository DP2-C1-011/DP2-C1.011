
package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Project extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "[A-Z]{3}-[0-9]{4}")
	@NotBlank
	@Column(unique = true)
	String						code;

	@NotBlank
	@Length(max = 76)
	String						title;

	@NotBlank
	@Length(max = 101)
	@Column(name = "Abstract")
	String						abstracto;

	String						fatalError;
	@NotNull
	@Min(0)
	Integer						cost;

	@URL
	String						link;

	Boolean						draftMode;

}
