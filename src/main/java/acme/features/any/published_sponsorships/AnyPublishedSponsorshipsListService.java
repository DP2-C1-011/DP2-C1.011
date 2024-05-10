
package acme.features.any.published_sponsorships;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsor.Sponsorship;

@Service
public class AnyPublishedSponsorshipsListService extends AbstractService<Any, Sponsorship> {

	@Autowired
	AnyPublishedSponsorshipsRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Sponsorship> objects;

		objects = this.repository.findAllPublishedSponsorships();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "moment", "startDate", "endDate", "amount", "financial", "email", "link", "draftMode");
		super.getResponse().addData(dataset);
	}
}
