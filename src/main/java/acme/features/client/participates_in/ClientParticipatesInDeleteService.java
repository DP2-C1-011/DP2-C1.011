
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
public class ClientParticipatesInDeleteService extends AbstractService<Client, ParticipatesIn> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientParticipatesInRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		ParticipatesIn participatesIn;
		Client client;

		masterId = super.getRequest().getData("id", int.class);
		participatesIn = this.repository.findParticipatesInById(masterId);
		client = participatesIn == null ? null : participatesIn.getClient();
		status = participatesIn != null && super.getRequest().getPrincipal().hasRole(client);

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

		Integer clientId;
		clientId = super.getRequest().getPrincipal().getActiveRoleId();
		Collection<Project> projects = this.repository.findManyProjectsByContractsClientId(clientId);
		super.state(!projects.contains(object.getProject()), "*", "client.participatesIn.error.contractError");
	}

	@Override
	public void perform(final ParticipatesIn object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final ParticipatesIn object) {
		assert object != null;

		int clientId;
		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset = new Dataset();

		clientId = super.getRequest().getPrincipal().getActiveRoleId();
		projects = this.repository.findAllProjects();
		choices = SelectChoices.from(projects, "code", object.getProject());
		dataset = super.unbind(object, "code");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}

}
