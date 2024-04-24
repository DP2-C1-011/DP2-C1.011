
package acme.features.sponsor.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsor.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoicePublishService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	SponsorInvoiceRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int sessionId;
		Invoice session;
		Sponsor developer;

		sessionId = super.getRequest().getData("id", int.class);
		session = this.repository.findInvoiceById(sessionId);
		developer = session == null ? null : session.getSponsorship().getSponsor();
		status = session != null && session.getFinancial() && super.getRequest().getPrincipal().hasRole(developer);

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
		super.bind(object, "code", "registrationDate", "dueDate", "quantity", "tax", "financial", "optionalLink");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;
		object.setFinancial(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "registrationDate", "dueDate", "quantity", "tax", "financial", "optionalLink");
		super.getResponse().addData(dataset);
	}

}
