
package acme.features.sponsor.invoice;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.sponsor.Invoice;
import acme.entities.sponsor.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceCreateService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	SponsorInvoiceRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int sponsorshipId;
		Sponsorship sponsorship;
		Sponsor sponsor;

		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		sponsorship = this.repository.findOneSponsorshipById(sponsorshipId);
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();
		status = sponsorship != null && sponsorship.getFinancial() && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Invoice object;
		int sponsorshipId;
		Sponsorship sponsorship;

		object = new Invoice();
		object.setFinancial(true);
		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		sponsorship = this.repository.findOneSponsorshipById(sponsorshipId);
		object.setSponsorship(sponsorship);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;
		super.bind(object, "code", "registrationDate", "dueDate", "quantity", "tax", "optionalLink");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice existing;

			existing = this.repository.findOneInvoiceByCode(object.getCode());
			super.state(existing == null, "code", "sponsor.invoice.form.error.duplicateCode");
		}

		if (!super.getBuffer().getErrors().hasErrors("registrationDate"))
			super.state(MomentHelper.isAfter(object.getRegistrationDate(), object.getSponsorship().getStart()), "start", "sponsor.invoice.form.error.startBeforeCreate");

		if (!super.getBuffer().getErrors().hasErrors("registrationDate") && !super.getBuffer().getErrors().hasErrors("dueDate")) {
			super.state(MomentHelper.isAfter(object.getDueDate(), object.getRegistrationDate()), "dueDate", "sponsor.invoice.form.error.finishBeforeStart");
			super.state(MomentHelper.isAfter(object.getDueDate(), MomentHelper.deltaFromMoment(object.getRegistrationDate(), 30, ChronoUnit.DAYS)), "dueDate", "sponsor.invoice.form.error.periodTooShort");
		}

	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "registrationDate", "dueDate", "quantity", "tax", "optionalLink");
		super.getResponse().addData(dataset);
		super.getResponse().addGlobal("sponsorshipId", super.getRequest().getData("sponsorshipId", int.class));
	}
}
