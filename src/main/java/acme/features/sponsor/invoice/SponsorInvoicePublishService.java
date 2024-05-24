
package acme.features.sponsor.invoice;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.components.MoneyService;
import acme.entities.sponsor.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoicePublishService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	SponsorInvoiceRepository	repository;
	@Autowired
	MoneyService				moneyService;


	@Override
	public void authorise() {
		boolean status;
		int invoiceId;
		Invoice invoice;
		Sponsor developer;

		invoiceId = super.getRequest().getData("id", int.class);
		invoice = this.repository.findInvoiceById(invoiceId);
		developer = invoice == null ? null : invoice.getSponsorship().getSponsor();
		status = invoice != null && invoice.getDraftMode() && super.getRequest().getPrincipal().hasRole(developer);

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
		super.bind(object, "code", "registrationDate", "dueDate", "quantity", "tax", "optionalLink");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("quantity")) {
			Boolean currencyState = this.moneyService.checkContains(object.getQuantity().getCurrency());
			super.state(currencyState, "quantity", "sponsor.invoice.form.error.invalid-currency");
		}

		if (!super.getBuffer().getErrors().hasErrors("registrationDate") && !super.getBuffer().getErrors().hasErrors("dueDate")) {
			super.state(MomentHelper.isAfter(object.getDueDate(), object.getRegistrationDate()), "*", "sponsor.invoice.form.error.finishBeforeStart");
			super.state(MomentHelper.isAfter(object.getDueDate(), MomentHelper.deltaFromMoment(object.getRegistrationDate(), 30, ChronoUnit.DAYS)), "*", "sponsor.invoice.form.error.periodTooShort");
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice existing;

			existing = this.repository.findOneInvoiceByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "sponsor.invoice.form.error.duplicateCode");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity")) {
			Boolean currencyState = this.moneyService.checkContains(object.getQuantity().getCurrency());
			super.state(currencyState, "quantity", "sponsor.invoice.form.error.invalid-currency");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity")) {
			Boolean currencyState = object.getQuantity().getCurrency().equals(object.getSponsorship().getAmount().getCurrency());
			super.state(currencyState, "quantity", "sponsor.invoice.form.error.different-currency");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity")) {
			Boolean currencyState = object.getQuantity().getAmount() > 0.00;
			super.state(currencyState, "quantity", "sponsor.invoice.form.error.negative-amount");
		}

		if (!super.getBuffer().getErrors().hasErrors("tax")) {
			Boolean taxState = object.getTax() < 100.00;
			super.state(taxState, "tax", "sponsor.invoice.form.error.invalid-tax");
		}
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;
		object.setDraftMode(false);
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
