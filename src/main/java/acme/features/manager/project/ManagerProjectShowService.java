
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectShowService extends AbstractService<Manager, Project> {

	@Autowired
	ManagerProjectRepository mpr;


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

		dataset = super.unbind(object, "code", "title", "abstracto", "fatalError", "cost", "link", "draft-mode");

		super.getResponse().addData(dataset);
	}

}
