
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.sponsor.Method;
import acme.entities.sponsor.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipListService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	SponsorSponsorshipRepository repository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Sponsor.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Sponsorship> objects;
		int id;

		id = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findSponsorshipBySponsorId(id);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;
		Dataset dataset;
		SelectChoices choices;

		choices = SelectChoices.from(Method.class, object.getFinancial());

		dataset = super.unbind(object, "code", "moment", "startDate", "endDate", "amount", "financial", "email", "link", "draftMode");

		dataset.put("methods", choices);
		super.getResponse().addData(dataset);
	}
}
