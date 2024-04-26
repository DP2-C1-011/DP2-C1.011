
package acme.form;

import java.util.List;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	private Integer				taxUnder21;
	private Integer				linkedSponsorships;

	private List<Object[]>		averageSponsorship;
	private List<Object[]>		deviationSponsorship;
	private List<Object[]>		minimumSponsorship;
	private List<Object[]>		maximumSponsorship;

	private List<Object[]>		averageInvoice;
	private List<Object[]>		deviationInvoice;
	private List<Object[]>		minimumInvoice;
	private List<Object[]>		maximumInvoice;

}
