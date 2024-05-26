
package acme.features.any.published_sponsorships;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsor.Sponsorship;

@Service
public class AnyPublishedSponsorshipsShowService extends AbstractService<Any, Sponsorship> {

	@Autowired
	AnyPublishedSponsorshipsRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int id;
		id = super.getRequest().getData("id", int.class);
		Sponsorship sponsorship = this.repository.findSponsorshipById(id);
		status = !sponsorship.getDraftMode();
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
	public void unbind(final Sponsorship object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code", "moment", "startDate", "endDate", "amount", "financial", "email", "link", "draftMode");
		super.getResponse().addData(dataset);
	}

}
