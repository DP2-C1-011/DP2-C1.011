
package acme.features.developer.training_module;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.training.DifficultyLevel;
import acme.entities.training.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleShowService extends AbstractService<Developer, TrainingModule> {

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
		status = module != null && super.getRequest().getPrincipal().hasRole(developer);

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
	public void unbind(final TrainingModule object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices projectChoices;

		Dataset dataset;

		projects = this.repository.findAllPublishedProjects();
		projectChoices = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "creation-moment", "details", "difficulty-level", "update-moment", "optional-link", "total-time", "draft-mode");

		SelectChoices choices;
		choices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());
		dataset.put("difficultyLevels", choices);
		dataset.put("project", projectChoices.getSelected().getKey());
		dataset.put("projects", projectChoices);
		super.getResponse().addGlobal("trainingModuleId", object.getId());
		super.getResponse().addData(dataset);
	}

}
