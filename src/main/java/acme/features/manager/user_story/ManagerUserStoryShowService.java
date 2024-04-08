
package acme.features.manager.user_story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryShowService extends AbstractService<Manager, UserStory> {

	@Autowired
	ManagerUserStoryRepository mur;


	@Override
	public void authorise() {
		boolean status;
		int id;
		UserStory userStory;
		id = super.getRequest().getData("id", int.class);
		userStory = this.mur.findUserStoryById(id);
		//comprobamos que la us existe y que la persona que intenta acceder tiene rol de manager.
		status = userStory != null && super.getRequest().getPrincipal().hasRole(userStory.getProject().getManager());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		//Se carga la us seleccionada por el manager y se guarda en el buffer
		UserStory object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.mur.findUserStoryById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "link", "draft-mode");

		super.getResponse().addData(dataset);
	}

}
