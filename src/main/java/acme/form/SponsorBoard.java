
package acme.form;

import javax.validation.constraints.NotNull;

import acme.client.data.AbstractForm;
import acme.client.data.datatypes.Money;

public class SponsorBoard extends AbstractForm {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@NotNull
	private Integer				taxUnder21;

	@NotNull
	private Integer				linkedSponsorships;

	@NotNull
	private Money				averageSponsorship;

	@NotNull
	private Money				deviationSponsorship;

	@NotNull
	private Money				minimumSponsorship;

	@NotNull
	private Money				maximumSponsorship;

	@NotNull
	private Money				averageInvoice;

	@NotNull
	private Money				deviationInvoice;

	@NotNull
	private Money				minimumInvoice;

	@NotNull
	private Money				maximumInvoice;

}
