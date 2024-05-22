
package acme.features.manager.userstory;

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
		boolean status;
		int id;
		Project p;
		Manager manager;

		id = super.getRequest().getData("projectId", int.class);
		p = this.mur.findOneProjectById(id);
		manager = p == null ? null : p.getManager();
		status = p != null && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
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
		super.getResponse().addGlobal("showCreate", false);

		super.getResponse().addData(dataset);

	}

}
