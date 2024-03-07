
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Claim extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	@NotBlank
	@Unique
	@Pattern(regexp = "C-[0-9]{4}")
	String						code;

	@NotNull
	@Past
	Date						instantiationMoment;

	@NotBlank
	@Length(max = 75)
	String						heading;

	@NotBlank
	@Length(max = 100)
	String						description;

	@NotBlank
	@Length(max = 100)
	String						department;

	@Email
	String						optionalEmailAddress;

	@URL
	String						optionalLink;

}
