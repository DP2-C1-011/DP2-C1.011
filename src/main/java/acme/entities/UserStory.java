
package acme.entities;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserStory extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 76)
	String						title;

	@NotBlank
	@Length(max = 101)
	String						description;

	@NotNull
	@Min(1)
	Integer						estimatedCost;

	@NotBlank
	@Length(max = 101)
	String						acceptanceCriteria;
	@URL
	String						link;

}
