
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
		boolean status;
		int id;
		Project project;
		//cada uno de los elementos de la lista son projects, por lo que si pulsamos en uno sacamos su id y le mostramos todos los atributos
		id = super.getRequest().getData("id", int.class);
		project = this.mpr.findProjectById(id);
		//comprobamos que el proyecto existe y que la persona que intenta acceder tiene rol de manager.
		status = project != null && super.getRequest().getPrincipal().hasRole(project.getManager());

		super.getResponse().setAuthorised(status);
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

		dataset = super.unbind(object, "code", "title", "abstracto", "fatal-error", "cost", "link", "draft-mode");

		super.getResponse().addData(dataset);
	}

}
