
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
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		//Si la persona tiene rol de manager se traeran todos las user-stories asociadas a sus proyectos
		Collection<UserStory> objects;
		int managerId;
		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.mur.findUserStoryByManagerId(managerId);
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;

		//project title puede dar error
		dataset = super.unbind(object, "title", "description");

		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<UserStory> objects) {
		assert objects != null;
		int masterId;
		Project project;
		final boolean showCreate;

		masterId = super.getRequest().getData("projectId", int.class);
		project = this.mur.findOneProjectById(masterId);
		showCreate = project.getDraftMode() && super.getRequest().getPrincipal().hasRole(project.getManager());

		super.getResponse().addGlobal("projectId", masterId);
		super.getResponse().addGlobal("showCreate", showCreate);
	}

}
