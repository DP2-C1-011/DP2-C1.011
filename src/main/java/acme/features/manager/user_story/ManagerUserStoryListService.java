
package acme.features.manager.user_story;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.project.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryListService extends AbstractService<Manager, UserStory> {

	@Autowired
	ManagerUserStoryRepository mur;


	@Override
	public void authorise() {
		//TODO Comprobar que el manager no puede acceder a proyectos que no sean suyos.
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		//Si la persona tiene rol de manager se traeran todos las user-stories asociadas a sus proyectos
		Collection<UserStory> objects;
		int projectId;
		projectId = super.getRequest().getData("projectId", int.class);
		objects = this.mur.findUserStoryByProjectId(projectId);
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;

		//project title puede dar error
		dataset = super.unbind(object, "title", "description");

		super.getResponse().addData(dataset);
		super.getResponse().addGlobal("projectId", object.getProject().getId());

	}

	@Override
	public void unbind(final Collection<UserStory> objects) {
		assert objects != null;
		int projectId;
		Project project;
		final boolean showCreate;

		projectId = super.getRequest().getData("projectId", int.class);
		project = this.mur.findOneProjectById(projectId);
		showCreate = project.getDraftMode() && super.getRequest().getPrincipal().hasRole(project.getManager());

		super.getResponse().addGlobal("projectId", projectId);
		super.getResponse().addGlobal("showCreate", showCreate);
	}

}
