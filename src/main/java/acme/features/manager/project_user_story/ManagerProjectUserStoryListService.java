
package acme.features.manager.project_user_story;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.project.ProjectUserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryListService extends AbstractService<Manager, ProjectUserStory> {

	@Autowired
	private ManagerProjectUserStoryRepository listRepository;


	@Override
	public void authorise() {
		int projectId = super.getRequest().getData("projectId", int.class);
		Project project = this.listRepository.findProjectById(projectId);

		Principal principal = super.getRequest().getPrincipal();
		int userAccountId = principal.getAccountId();

		boolean status = project != null && principal.hasRole(Manager.class) && project.getManager().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		int projectId = super.getRequest().getData("projectId", int.class);
		Collection<ProjectUserStory> links = this.listRepository.findLinksByProjectId(projectId);
		super.getBuffer().addData(links);
	}
	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;
		Dataset dataset = super.unbind(object, "userStory");
		super.getResponse().addData(dataset);
	}
}
