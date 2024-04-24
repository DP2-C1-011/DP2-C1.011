
package acme.features.sponsor.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsor.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceDeleteService extends AbstractService<Sponsor, Invoice> {

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
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "registrationDate", "dueDate", "quantity", "tax", "draftMode", "optionalLink");
		super.getResponse().addData(dataset);
	}
}
