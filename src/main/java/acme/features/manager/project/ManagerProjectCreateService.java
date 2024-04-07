
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectCreateService extends AbstractService<Manager, Project> {

	//Poner validación de que coste es mayor que 0
	//Poner validación de que el código del proyecto es unico
	@Autowired
	ManagerProjectRepository mpr;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	// carga un project vacio y le añade draftmode y manager
	@Override
	public void load() {
		Project object;
		Manager manager;

		manager = this.mpr.findOneManagerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Project();
		object.setDraftMode(true);
		object.setManager(manager);

		super.getBuffer().addData(object);
	}

	// coge todos los datos del formulario excepto manager y draftmode que ya lo hemos tocado antes y lo pone en un objeto
	@Override
	public void bind(final Project object) {
		assert object != null;
		super.bind(object, "code", "title", "abstracto", "fatal-error", "cost", "link");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Project existing;

			existing = this.mpr.findOneProjectByCode(object.getCode());
			super.state(existing == null, "code", "manager.project.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("cost"))
			super.state(object.getCost().getAmount() > 0, "cost", "manager.project.form.error.negative-salary");
	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		this.mpr.save(object);
	}

	//coge el objeto y lo pone en el formulario
	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code", "title", "abstracto", "fatal-error", "cost", "link", "draft-mode");
		super.getResponse().addData(dataset);
	}

}
