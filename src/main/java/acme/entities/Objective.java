
package acme.entities;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;

public class Objective extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Past
	@NotNull
	private Date				instantiationMoment;

	@NotBlank
	@NotNull
	@Length(max = 75)
	private String				title;

	@NotBlank
	@NotNull
	@Length(max = 100)
	private String				description;

	@NotNull
	private Priority			priority;

	@NotNull
	private Boolean				isCritical;

	@NotNull
	private Date				startDate;

	@NotNull
	private Date				endDate;

	@URL
	private String				optionalLink;

}
