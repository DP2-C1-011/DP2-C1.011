
package acme.entities.training;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.roles.Developer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TrainingModule extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@Valid
	@ManyToOne()
	private Developer			developer;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	private String				code;

	@Past
	@NotNull
	private Date				creationMoment;

	@NotBlank
	@Length(max = 100)
	private String				details;

	private DifficultyLevel		difficultyLevel;

	@Past
	//Comprobar en el service que es posterior a creationMoment
	private Date				updateMoment;

	@URL
	private String				optionalLink;

	@Transient
	private double				totalTime;

	private boolean				draftMode;
}
