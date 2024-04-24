
package acme.form;

import java.util.Map;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	private Integer				taxUnder21;
	private Integer				linkedSponsorships;

	private Map<String, Double>	averageSponsorship;
	private Map<String, Double>	deviationSponsorship;
	private Map<String, Double>	minimumSponsorship;
	private Map<String, Double>	maximumSponsorship;

	private Map<String, Double>	averageInvoice;
	private Map<String, Double>	deviationInvoice;
	private Map<String, Double>	minimumInvoice;
	private Map<String, Double>	maximumInvoice;

}
