
package acme.features.manager.user_story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.UsPriority;
import acme.entities.project.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryDeleteService extends AbstractService<Manager, UserStory> {

	@Autowired
	ManagerUserStoryRepository mur;


	@Override
	public void authorise() {
		boolean status;
		int id;
		UserStory us;
		Manager manager;

		id = super.getRequest().getData("id", int.class);
		us = this.mur.findUserStoryById(id);
		manager = us == null ? null : us.getProject().getManager();
		status = us != null && us.getDraftMode() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		UserStory object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.mur.findUserStoryById(id);

		super.getBuffer().addData(object);
	}

	// coge todos los datos del formulario excepto project
	@Override
	public void bind(final UserStory object) {
		assert object != null;
		super.bind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "link", "priority");

	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;
		this.mur.delete(object);
	}

	//coge el objeto y lo pone en el formulario
	@Override
	public void unbind(final UserStory object) {
		assert object != null;
		Dataset dataset;
		SelectChoices choices;

		choices = SelectChoices.from(UsPriority.class, object.getPriority());
		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "link", "draft-mode");
		dataset.put("priority", choices.getSelected().getKey());
		dataset.put("priorities", choices);
		dataset.put("masterId", object.getProject());
		super.getResponse().addData(dataset);

	}

}
