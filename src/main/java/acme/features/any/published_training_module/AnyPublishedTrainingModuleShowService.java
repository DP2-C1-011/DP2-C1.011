
package acme.features.any.published_training_module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.training.TrainingModule;

@Service
public class AnyPublishedTrainingModuleShowService extends AbstractService<Any, TrainingModule> {

	@Autowired
	AnyPublishedTrainingModuleRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
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

		Dataset dataset;
		dataset = super.unbind(object, "code", "creation-moment", "details", "difficulty-level", "update-moment", "optional-link", "total-time", "draft-mode");
		dataset.put("project", object.getProject().getCode());
		super.getResponse().addData(dataset);
	}

}
