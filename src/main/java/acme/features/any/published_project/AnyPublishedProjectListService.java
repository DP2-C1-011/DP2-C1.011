
package acme.features.any.published_project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;

@Service
public class AnyPublishedProjectListService extends AbstractService<Any, Project> {

	@Autowired
	AnyPublishedProjectRepository mpr;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Any.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Project> objects;
		objects = this.mpr.findAllPublishedProjects();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title");

		super.getResponse().addData(dataset);
	}

}
