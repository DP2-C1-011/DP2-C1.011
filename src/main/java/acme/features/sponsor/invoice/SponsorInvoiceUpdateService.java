
package acme.features.sponsor.invoice;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.sponsor.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceUpdateService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	SponsorInvoiceRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int invoiceId;
		Invoice invoice;
		Sponsor sponsor;

		invoiceId = super.getRequest().getData("id", int.class);
		invoice = this.repository.findInvoiceById(invoiceId);
		sponsor = invoice == null ? null : invoice.getSponsorship().getSponsor();
		status = invoice != null && invoice.getDraftMode() && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Invoice object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findInvoiceById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;
		super.bind(object, "code", "registrationDate", "dueDate", "quantity", "tax", "optionalLink", "draftMode");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(object.getDraftMode(), "draftMode", "sponsor.invoice.form.error.draftMode");

		if (!super.getBuffer().getErrors().hasErrors("registrationDate"))
			super.state(MomentHelper.isAfter(object.getRegistrationDate(), object.getSponsorship().getStartDate()), "registrationDate", "sponsor.invoice.form.error.registrationDateBeforeCreate");

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
		dataset = super.unbind(object, "code", "registrationDate", "dueDate", "quantity", "tax", "optionalLink", "draftMode");
		super.getResponse().addData(dataset);
	}
}
