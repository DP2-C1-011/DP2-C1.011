
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
public class ManagerUserStoryCreateService extends AbstractService<Manager, UserStory> {

	@Autowired
	ManagerUserStoryRepository mur;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	//Cargamos un nuevo objeto vacio de userStory y le añadimos el proyecto al que pertenece
	@Override
	public void load() {
		UserStory object;
		Manager manager;

		manager = this.mur.findOneManagerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new UserStory();
		object.setDraftMode(true);
		object.setManager(manager);

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
		if (!super.getBuffer().getErrors().hasErrors("estimatedCost"))
			super.state(object.getEstimatedCost() > 1, "estimatedCost", "manager.user-story.form.error.negative-cost");
		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(object.getDraftMode(), "draftMode", "manager.project.form.error.draft-mode");
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;

		this.mur.save(object);
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
		super.getResponse().addData(dataset);

	}

}
