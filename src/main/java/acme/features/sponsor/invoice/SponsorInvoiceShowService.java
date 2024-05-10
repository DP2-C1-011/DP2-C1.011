
package acme.features.sponsor.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsor.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceShowService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	SponsorInvoiceRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int id;
		Invoice inv;
		Sponsor sponsor;

		id = super.getRequest().getData("id", int.class);
		inv = this.repository.findInvoiceById(id);

		sponsor = inv == null ? null : inv.getSponsorship().getSponsor();
		status = inv != null && super.getRequest().getPrincipal().hasRole(sponsor);

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
	public void unbind(final Invoice object) {
		assert object != null;
		Dataset dataset;

		dataset = super.unbind(object, "code", "registrationDate", "dueDate", "quantity", "tax", "optionalLink", "draftMode");
		dataset.put("totalAmount", object.getTotalAmount());

		super.getResponse().addData(dataset);
	}
}
