
package acme.entities.training;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.roles.Developer;
import lombok.Getter;
import lombok.Setter;

/*
 * @Table(indexes = {
 * 
 * @Index(columnList = "code")
 * })
 */
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

	@NotNull
	private DifficultyLevel		difficultyLevel;

	@Past
	private Date				updateMoment;

	@URL
	@Length(max = 255)
	private String				optionalLink;

	@NotNull
	@Positive
	@Max(2000)
	private Integer				totalTime;

	private Boolean				draftMode;
}
