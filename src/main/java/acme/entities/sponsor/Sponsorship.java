
package acme.entities.sponsor;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.roles.Sponsor;

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

	@NotNull
	private Date				start;

	@NotNull
	private Date				end;

	@NotNull
	private Integer				duration;

	@NotNull
	private Money				amount;

	@NotNull
	private Boolean				financial;

	@Email
	private String				optionalEmail;

	@URL
	private String				optionalLink;

	@NotNull
	@ManyToOne
	private Sponsor				sponsor;


	public Integer duration() {
		int res = (int) (this.end.getTime() - this.start.getTime());
		return Integer.valueOf(res);
	}
}
