
package acme.features.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsor.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipPublishService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	SponsorSponsorshipRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int sponsorshipId;
		Sponsorship sponsorship;
		Sponsor sponsor;

		sponsorshipId = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findSponsorshipById(sponsorshipId);
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();
		status = sponsorship != null && sponsorship.getFinancial() && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Sponsorship object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findSponsorshipById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;
		super.bind(object, "code", "start", "end", "duration", "amount", "financial", "optionalEmail", "optionalLink");
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;
		int sponsorshipId;
		sponsorshipId = super.getRequest().getData("id", int.class);

		if (!super.getBuffer().getErrors().hasErrors("invoices")) {
			Integer numInvoices = this.repository.findInvoicesBySponsorshipId(sponsorshipId).size();
			super.state(numInvoices > 0, "invoices", "sponsor.invoice.form.error.invoice");
		}
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;
		object.setFinancial(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "start", "end", "duration", "amount", "financial", "optionalEmail", "optionalLink");

		super.getResponse().addGlobal("sponsorshipId", object.getId());
		super.getResponse().addData(dataset);
	}

}
