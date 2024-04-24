
package acme.features.client.participates_in;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.ParticipatesIn;
import acme.entities.project.Project;
import acme.roles.Client;

@Service
public class ClientParticipatesInShowService extends AbstractService<Client, ParticipatesIn> {

	@Autowired
	ClientParticipatesInRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		ParticipatesIn participatesIn;
		Client client;

		masterId = super.getRequest().getData("id", int.class);
		participatesIn = this.repository.findParticipatesInById(masterId);
		client = participatesIn == null ? null : participatesIn.getClient();
		status = super.getRequest().getPrincipal().hasRole(client) || participatesIn != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ParticipatesIn object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findParticipatesInById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final ParticipatesIn object) {

		assert object != null;
		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;

		projects = this.repository.findAllProjects();
		choices = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);

	}

}
