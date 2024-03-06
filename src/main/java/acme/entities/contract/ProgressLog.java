
package acme.entities.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ProgressLog extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@NotNull
	@Column(unique = true)
	@Pattern(regexp = "PG-[A-Z]{1,2}-[0-9]{4}")
	private String				recordId;

	@Min(0)
	@Max(100)
	@NotNull
	private Integer				completeness;

	@NotBlank
	@Length(max = 100)
	@NotNull
	private String				comment;

	@Past
	@NotNull
	private Date				registartionMoment;

	@NotBlank
	@Length(max = 75)
	@NotNull
	private String				responsible;

	@ManyToOne
	@NotNull
	private Contract			contract;

}
