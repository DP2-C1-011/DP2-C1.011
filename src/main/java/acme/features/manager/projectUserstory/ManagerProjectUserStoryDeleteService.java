
package acme.features.manager.projectUserstory;

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
public class ManagerProjectUserStoryDeleteService extends AbstractService<Manager, ProjectUserStory> {

	@Autowired
	private ManagerProjectUserStoryRepository mur;


	@Override
	public void authorise() {
		boolean status;
		/*
		 * int id;
		 * ProjectUserStory pus;
		 * Manager manager;
		 * 
		 * id = super.getRequest().getData("id", int.class);
		 * pus = this.mur.findLinkById(id);
		 * manager = pus == null ? null : pus.getProject().getManager();
		 * status = pus != null && super.getRequest().getPrincipal().hasRole(manager);
		 * 
		 * super.getResponse().setAuthorised(status);
		 */
		super.getResponse().setAuthorised(true);
	}
	@Override
	public void load() {
		int userStoryId = super.getRequest().getData("userStoryId", int.class);
		UserStory us = this.mur.findUserStoryById(userStoryId);

		ProjectUserStory object = new ProjectUserStory();
		object.setUserStory(us);

		super.getBuffer().addData(object);
	}
	@Override
	public void bind(final ProjectUserStory object) {
		assert object != null;
		int projectId;
		int userStoryId;
		Project project;
		projectId = super.getRequest().getData("project", int.class);
		project = this.mur.findProjectById(projectId);
		userStoryId = super.getRequest().getData("userStoryId", int.class);
		UserStory us = this.mur.findUserStoryById(userStoryId);
		object.setUserStory(us);
		object.setProject(project);

	}
	@Override
	public void validate(final ProjectUserStory object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(object.getProject() != null, "project", "manager.project-user-story.form.error.projectNotNull");
		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(object.getProject().getDraftMode(), "project", "manager.project-user-story.form.error.project");
	}
	@Override
	public void perform(final ProjectUserStory object) {
		assert object != null;
		final ProjectUserStory pus = this.mur.findProjectUserStoryByProjectUserStory(object.getProject(), object.getUserStory());
		this.mur.delete(pus);
	}
	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;
		int userStoryId = super.getRequest().getData("userStoryId", int.class);

		Manager manager = this.mur.findOneManagerByUserStoryId(userStoryId);
		Collection<Project> projects = this.mur.findProjectsByManagerId(manager.getId());

		SelectChoices choices = SelectChoices.from(projects, "title", object.getProject());

		Dataset dataset = super.unbind(object, "userStory");

		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		dataset.put("userStoryId", userStoryId);

		super.getResponse().addData(dataset);

	}
}
