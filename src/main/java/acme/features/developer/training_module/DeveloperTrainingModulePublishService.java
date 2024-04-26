
package acme.features.developer.training_module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
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
		super.bind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "totalTime", "optionalLink");
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
		int moduleId;
		Integer tsInTm, totalPublishedTm;
		
		moduleId = super.getRequest().getData("id", int.class);

		if (!super.getBuffer().getErrors().hasErrors("sessions")) {
			Integer numSessions = this.repository.findTrainingSessionsByTrainingModuleId(moduleId).size();
			super.state(numSessions > 0, "*", "developer.training-module.form.error.training-session");
		}
		
		tsInTm = this.repository.findTrainingSessionsByTrainingModuleId(object.getId()).size();
		totalPublishedTm = this.repository.findPublishedTrainingSessionsByTrainingModuleId(object.getId()).size();
		super.state(tsInTm != null && totalPublishedTm == tsInTm, "*", "developer.training-module.form.error.not-published-trainingSessions");
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;
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
		super.getResponse().addGlobal("trainingModuleId", object.getId());
		super.getResponse().addData(dataset);
	}

}
