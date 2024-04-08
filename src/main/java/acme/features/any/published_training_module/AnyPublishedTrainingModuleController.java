
package acme.features.any.published_training_module;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.training.TrainingModule;

@Controller
public class AnyPublishedTrainingModuleController extends AbstractController<Any, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyPublishedTrainingModuleListService	listService;

	@Autowired
	private AnyPublishedTrainingModuleShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
