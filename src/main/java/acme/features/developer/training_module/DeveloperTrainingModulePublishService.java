
package acme.features.developer.training_module;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.training.DifficultyLevel;
import acme.entities.training.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModulePublishService extends AbstractService<Developer, TrainingModule> {

	@Autowired
	DeveloperTrainingModuleRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int moduleId;
		TrainingModule module;
		Developer developer;

		moduleId = super.getRequest().getData("id", int.class);
		module = this.repository.findTrainingModuleById(moduleId);
		developer = module == null ? null : module.getDeveloper();
		status = module != null && module.getDraftMode() && super.getRequest().getPrincipal().hasRole(developer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingModuleById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "totalTime", "optionalLink");
		object.setProject(project);
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
		int moduleId;
		Integer tsInTm, totalPublishedTm;

		moduleId = super.getRequest().getData("id", int.class);

		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(object.getProject().getDraftMode().equals(false), "project", "developer.training-module.form.error.not-published-project");

		if (!super.getBuffer().getErrors().hasErrors("sessions")) {
			Integer numSessions = this.repository.findTrainingSessionsByTrainingModuleId(moduleId).size();
			super.state(numSessions > 0, "*", "developer.training-module.form.error.training-session");
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule existing;

			existing = this.repository.findTrainingModuleByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "developer.training-module.form.error.duplicateCode");
		}

		tsInTm = this.repository.findTrainingSessionsByTrainingModuleId(object.getId()).size();
		totalPublishedTm = this.repository.findPublishedTrainingSessionsByTrainingModuleId(object.getId()).size();
		super.state(tsInTm != null && totalPublishedTm == tsInTm, "*", "developer.training-module.form.error.not-published-trainingSessions");
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;
		TrainingModule objectOld = this.repository.findTrainingModuleById(object.getId());
		object.setCreationMoment(objectOld.getCreationMoment());
		object.setUpdateMoment(MomentHelper.getCurrentMoment());
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "optionalLink", "totalTime", "draftMode");

		SelectChoices choices;
		choices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());
		dataset.put("difficultyLevels", choices);

		Collection<Project> projects;
		SelectChoices projectChoices;
		projects = this.repository.findAllPublishedProjects();
		projectChoices = SelectChoices.from(projects, "code", object.getProject());
		dataset.put("project", projectChoices.getSelected().getKey());
		dataset.put("projects", projectChoices);

		super.getResponse().addGlobal("trainingModuleId", object.getId());
		super.getResponse().addData(dataset);
	}

}
