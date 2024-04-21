
package acme.features.manager.project_user_story;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.project.ProjectUserStory;
import acme.roles.Manager;

@Controller
public class ManagerProjectUserStoryController extends AbstractController<Manager, ProjectUserStory> {

	@Autowired
	private ManagerProjectUserStoryListService	listService;
	@Autowired
	private ManagerProjectUserStoryShowService	showService;
	@Autowired
	private ManagerProjectUserStoryShowService	createService;
	@Autowired
	private ManagerProjectUserStoryShowService	deleteService;


	@PostConstruct
	public void initialise() {
		super.addCustomCommand("list-by-project", "list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
	}
}
