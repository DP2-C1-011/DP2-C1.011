
package acme.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Risk extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "R-[0-9]{3}")
	private String				reference;

	@Past
	private Date				identificationDate;

	@Min(1)
	private Integer				impact;

	@Min(0)
	@Max(1)
	@Digits(integer = 1, fraction = 2)
	private double				probability;


	@Transient
	public double getValue() {

		return this.impact * this.probability;

	}


	@NotBlank
	@Max(100)
	private String	description;

	@URL
	private String	optionalLink;

}
