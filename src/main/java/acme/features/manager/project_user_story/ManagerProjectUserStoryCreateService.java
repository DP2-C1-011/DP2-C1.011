
package acme.features.manager.project_user_story;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		super.getResponse().setAuthorised(true);
	}
	@Override
	public void load() {
		int userStoryId = super.getRequest().getData("userStoryId", int.class);
		UserStory us = this.createRepository.findUserStoryById(userStoryId);

		ProjectUserStory object = new ProjectUserStory();
		object.setUserStory(us);

		super.getBuffer().addData(object);
	}
	@Override
	public void bind(final ProjectUserStory object) {
		assert object != null;
		int projectId;
		Project project;
		projectId = super.getRequest().getData("project", int.class);
		project = this.createRepository.findProjectById(projectId);
		super.bind(object, "id");
		object.setProject(project);
	}
	@Override
	public void validate(final ProjectUserStory object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("UserStory") && !super.getBuffer().getErrors().hasErrors("Project")) {
			//int masterId;
			//masterId = super.getRequest().getData("masterId", int.class);
			final Collection<UserStory> us = this.createRepository.findUserStoryByProject(object.getProject().getId());
			super.state(us.isEmpty() || !us.contains(object.getUserStory()), "Project", "manager.ProjectUserStory.form.error.UserStory");
		}
		if (!super.getBuffer().getErrors().hasErrors("Project"))
			super.state(object.getProject().getDraftMode(), "Project", "manager.ProjectUserStory.form.error.Project");
	}
	@Override
	public void perform(final ProjectUserStory object) {
		assert object != null;
		this.createRepository.save(object);
	}
	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;
		int userStoryId = super.getRequest().getData("userStoryId", int.class);

		Collection<Project> projects = this.createRepository.findProjects();

		SelectChoices choices = SelectChoices.from(projects, "title", object.getProject());

		Dataset dataset = super.unbind(object, "userStory");

		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		dataset.put("userStoryId", userStoryId);

		super.getResponse().addData(dataset);
		super.getResponse().addGlobal("userStoryId", super.getRequest().getData("userStoryId", int.class));

	}

}
