
package acme.features.manager.project_user_story;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.project.ProjectUserStory;
import acme.entities.project.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryCreateService extends AbstractService<Manager, ProjectUserStory> {

	@Autowired
	private ManagerProjectUserStoryRepository createRepository;


	@Override
	public void authorise() {
		int projectId = super.getRequest().getData("id", int.class);
		Project project = this.createRepository.findProjectById(projectId);

		Principal principal = super.getRequest().getPrincipal();
		int userAccountId = principal.getAccountId();

		boolean status = project != null && project.getDraftMode() && principal.hasRole(Manager.class) && project.getManager().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		int projectId = super.getRequest().getData("projectId", int.class);
		Project project = this.createRepository.findProjectById(projectId);

		ProjectUserStory object = new ProjectUserStory();
		object.setProject(project);

		super.getBuffer().addData(object);
	}
	@Override
	public void bind(final ProjectUserStory object) {
		assert object != null;
		super.bind(object, "userStory");
	}
	@Override
	public void validate(final ProjectUserStory object) {
		assert object != null;
		int projectId = super.getRequest().getData("projectId", int.class);

		if (!super.getBuffer().getErrors().hasErrors("userStory")) {
			boolean duplicatedUserStory = this.createRepository.findLinksByProjectId(projectId).stream().anyMatch(x -> x.getUserStory().equals(object.getUserStory()));
			super.state(!duplicatedUserStory, "userStory", "manager.project.form.error.duplicated-user-story");
		}
	}
	@Override
	public void perform(final ProjectUserStory object) {
		assert object != null;
		this.createRepository.save(object);
	}
	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;
		int projectId = super.getRequest().getData("projectId", int.class);

		int managerId = object.getProject().getManager().getUserAccount().getId();
		Collection<UserStory> userStories = this.createRepository.findUserStoriesByManagerId(managerId).stream().filter(x -> !this.createRepository.findLinksByProjectId(projectId).stream().map(ProjectUserStory::getUserStory).anyMatch(x2 -> x2.equals(x)))
			.toList();

		SelectChoices choices = SelectChoices.from(userStories, "title", object.getUserStory());

		Dataset dataset = super.unbind(object, "userStory");

		dataset.put("userStories", choices);
		dataset.put("projectId", projectId);

		super.getResponse().addData(dataset);
	}
}
