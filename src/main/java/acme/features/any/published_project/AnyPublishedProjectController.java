
package acme.features.any.published_project;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.project.Project;

@Controller
public class AnyPublishedProjectController extends AbstractController<Any, Project> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyPublishedProjectListService	listService;

	@Autowired
	private AnyPublishedProjectShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
