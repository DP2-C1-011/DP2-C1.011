
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Notice extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	private Date				instantiationMoment;

	@NotNull
	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotNull
	@NotBlank
	@Length(max = 75)
	private String				author;

	@NotNull
	@NotBlank
	@Length(max = 100)
	private String				message;

	@Email
	private String				optionalEmail;

	@URL
	private String				optionalLink;

}
