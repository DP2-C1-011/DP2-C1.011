
package acme.features.developer.training_session;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.training.TrainingModule;
import acme.entities.training.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionListService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Developer.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<TrainingSession> objects;
		int moduleId;

		moduleId = super.getRequest().getData("trainingModuleId", int.class);
		objects = this.repository.findTrainingSessionsByTrainingModuleId(moduleId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "startMoment", "finishMoment", "location", "instructor", "contactEmail", "optionalLink");
		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<TrainingSession> objects) {
		assert objects != null;
		int moduleId;
		TrainingModule tm;

		moduleId = super.getRequest().getData("trainingModuleId", int.class);
		tm = this.repository.findTrainingModuleById(moduleId);

		super.getResponse().addGlobal("trainingModuleId", moduleId);
	}
}
