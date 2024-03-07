
package acme.entities.sponsor;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;

public class Invoice extends AbstractEntity {

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
	private Date				registrationDate;

	@Past
	@NotNull
	private Date				dueDate;

	@NotNull
	private Money				quantity;

	@NotNull
	private Double				tax;

	@NotNull
	private Boolean				financial;

	@Email
	private String				optionalEmail;

	@URL
	private String				optionalLink;

	@NotNull
	@ManyToOne
	private Sponsorship			sponsorship;


	@Transient
	public Money total() {
		Money m = null;
		Double res = this.quantity.getAmount() * (1. + this.tax);
		m.setAmount(res);
		m.setCurrency(this.quantity.getCurrency());
		return m;
	}
}
