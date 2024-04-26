
package acme.entities.sponsor;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.project.Project;
import acme.roles.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsorship extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@NotNull
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	@NotBlank
	@Column(unique = true)
	private String				code;

	@Past
	@NotNull
	private Date				start;

	@Future
	@NotNull
	private Date				end;

	@NotNull
	private Money				amount;

	@NotNull
	private Boolean				financial;

	@Email
	private String				optionalEmail;

	@URL
	private String				optionalLink;

	@NotNull
	@Valid
	@ManyToOne
	private Sponsor				sponsor;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project				project;


	@Transient
	public Integer duration() {
		int res = (int) (this.end.getTime() - this.start.getTime());
		return Integer.valueOf(res);
	}
}
