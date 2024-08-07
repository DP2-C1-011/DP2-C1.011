
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.sponsor.Method;
import acme.entities.sponsor.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipShowService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	SponsorSponsorshipRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int id;
		Sponsorship spo;
		Sponsor sponsor;

		id = super.getRequest().getData("id", int.class);
		spo = this.repository.findSponsorshipById(id);
		sponsor = spo == null ? null : spo.getSponsor();

		status = spo != null && super.getRequest().getPrincipal().hasRole(sponsor);

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
		SelectChoices choices;

		choices = SelectChoices.from(Method.class, object.getFinancial());

		dataset = super.unbind(object, "code", "moment", "startDate", "endDate", "amount", "financial", "email", "link", "draftMode");

		dataset.put("methods", choices);

		Collection<Project> projects;
		SelectChoices projectChoices;

		projects = this.repository.findAllPublishedProjects();
		projectChoices = SelectChoices.from(projects, "code", object.getProject());

		dataset.put("project", projectChoices.getSelected().getKey());
		dataset.put("projects", projectChoices);

		super.getResponse().addData(dataset);
	}

}
