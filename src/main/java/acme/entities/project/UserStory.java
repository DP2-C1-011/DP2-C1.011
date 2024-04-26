
package acme.entities.project;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.roles.Manager;
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
	@Length(max = 75)
	String						title;

	@NotBlank
	@Length(max = 100)
	String						description;

	@NotNull
	@Min(1)
	Integer						estimatedCost;

	@NotBlank
	@Length(max = 100)
	String						acceptanceCriteria;
	@URL
	String						link;

	Boolean						draftMode;

	@NotNull
	UsPriority					priority;

	@NotNull
	@ManyToOne(optional = false)
	Manager						manager;

}
