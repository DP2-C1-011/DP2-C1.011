
package acme.form;

import acme.client.data.AbstractForm;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private Integer				taxUnder21;
	private Integer				linkedSponsorships;

	private Money				averageSponsorship;
	private Money				deviationSponsorship;
	private Money				minimumSponsorship;
	private Money				maximumSponsorship;

	private Money				averageInvoice;
	private Money				deviationInvoice;
	private Money				minimumInvoice;
	private Money				maximumInvoice;

}
