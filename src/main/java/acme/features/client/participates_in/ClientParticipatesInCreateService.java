
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
public class ClientParticipatesInCreateService extends AbstractService<Client, ParticipatesIn> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientParticipatesInRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		ParticipatesIn object;
		Client client;

		client = this.repository.findOneClientById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new ParticipatesIn();
		object.setClient(client);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ParticipatesIn object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);
		super.bind(object, "code");
		object.setProject(project);
	}

	@Override
	public void validate(final ParticipatesIn object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("project")) {
			ParticipatesIn existing;

			existing = this.repository.findOneParticipatesInByClientAndProject(object.getClient().getId(), object.getProject().getId());
			super.state(existing == null, "project", "client.participatesIn.form.error.duplicated");
		}

	}

	@Override
	public void perform(final ParticipatesIn object) {
		assert object != null;

		this.repository.save(object);
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
