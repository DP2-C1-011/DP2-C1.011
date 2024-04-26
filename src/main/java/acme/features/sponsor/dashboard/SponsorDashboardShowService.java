
package acme.features.sponsor.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.form.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {

	@Autowired
	private SponsorDashboardRepository r;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Sponsor.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Integer id;

		SponsorDashboard sponsorDashboard;

		Integer taxUnder21;
		Integer linkedSponsorships;

		List<Object[]> averageSponsorship;
		List<Object[]> deviationSponsorship;
		List<Object[]> minimumSponsorship;
		List<Object[]> maximumSponsorship;

		List<Object[]> averageInvoice;
		List<Object[]> deviationInvoice;
		List<Object[]> minimumInvoice;
		List<Object[]> maximumInvoice;

		id = super.getRequest().getPrincipal().getActiveRoleId();
		taxUnder21 = this.r.getTaxUnder21(id);
		linkedSponsorships = this.r.getLinkedSponsorships(id);

		averageSponsorship = this.r.avgSponsorshipAmount(id);
		deviationSponsorship = this.r.stdSponsorshipAmount(id);
		minimumSponsorship = this.r.minSponsorshipAmount(id);
		maximumSponsorship = this.r.maxSponsorshipAmount(id);

		averageInvoice = this.r.avgInvoiceAmount(id);
		deviationInvoice = this.r.stdInvoiceAmount(id);
		minimumInvoice = this.r.minInvoiceAmount(id);
		maximumInvoice = this.r.maxInvoiceAmount(id);

		sponsorDashboard = new SponsorDashboard();

		sponsorDashboard.setTaxUnder21(taxUnder21);
		sponsorDashboard.setLinkedSponsorships(linkedSponsorships);

		sponsorDashboard.setAverageSponsorship(averageSponsorship);
		sponsorDashboard.setDeviationSponsorship(deviationSponsorship);
		sponsorDashboard.setMinimumSponsorship(minimumSponsorship);
		sponsorDashboard.setMaximumSponsorship(maximumSponsorship);

		sponsorDashboard.setAverageInvoice(averageInvoice);
		sponsorDashboard.setDeviationInvoice(deviationInvoice);
		sponsorDashboard.setMinimumInvoice(minimumInvoice);
		sponsorDashboard.setMaximumInvoice(maximumInvoice);

		super.getBuffer().addData(sponsorDashboard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {
		Dataset dataset;
		dataset = super.unbind(object, "taxUnder21", "linkedSponsorships", "averageSponsorship", "deviationSponsorship", "minimumSponsorship", "maximumSponsorship", "averageInvoice", "deviationInvoice", "minimumInvoice", "maximumInvoice");
		super.getResponse().addData(dataset);
	}

}
