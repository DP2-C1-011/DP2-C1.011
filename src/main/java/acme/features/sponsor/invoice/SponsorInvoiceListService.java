
package acme.features.sponsor.invoice;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsor.Invoice;
import acme.entities.sponsor.Sponsorship;
import acme.features.sponsor.sponsorship.SponsorSponsorshipRepository;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceListService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	SponsorInvoiceRepository		repository;

	@Autowired
	SponsorSponsorshipRepository	sporep;


	@Override
	public void authorise() {
		boolean status;

		int id;
		Sponsorship spo;
		Sponsor sponsor;

		id = super.getRequest().getData("sponsorshipId", int.class);
		spo = this.sporep.findSponsorshipById(id);
		sponsor = spo == null ? null : spo.getSponsor();

		status = spo != null && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Invoice> objects;
		int sponsorshipId;

		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		objects = this.repository.findInvoiceBySponsorship(sponsorshipId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "registrationDate", "dueDate", "quantity", "tax", "optionalLink", "draftMode");
		super.getResponse().addData(dataset);
		super.getResponse().addGlobal("sponsorshipId", object.getSponsorship().getId());
	}

	@Override
	public void unbind(final Collection<Invoice> objects) {
		assert objects != null;
		int sponsorshipId;
		Sponsorship sp;
		final boolean showCreate;

		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		sp = this.repository.findOneSponsorshipById(sponsorshipId);
		showCreate = sp.getDraftMode() && super.getRequest().getPrincipal().hasRole(Sponsor.class);

		super.getResponse().addGlobal("sponsorshipId", sponsorshipId);
		super.getResponse().addGlobal("showCreate", showCreate);
	}
}
