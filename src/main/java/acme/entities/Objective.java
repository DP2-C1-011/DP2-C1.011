
package acme.entities;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;

public class Objective extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Past
	private Date				instantiationMoment;

	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotBlank
	@Length(max = 100)
	private String				description;

	private Priority			priority;

	private Boolean				isCritical;

	private Date				startDate;

	private Date				endDate;

	@URL
	private String				optionalLink;

}
