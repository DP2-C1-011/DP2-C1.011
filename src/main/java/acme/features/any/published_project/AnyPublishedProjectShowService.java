
package acme.features.any.published_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;

@Service
public class AnyPublishedProjectShowService extends AbstractService<Any, Project> {

	@Autowired
	AnyPublishedProjectRepository mpr;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		//Se carga el proyecto seleccionado por el manager y se guarda en el buffer
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.mpr.findProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "abstracto", "fatalError", "cost", "link", "draft-mode", "systemCurrencyBudget");

		super.getResponse().addData(dataset);
	}

}
