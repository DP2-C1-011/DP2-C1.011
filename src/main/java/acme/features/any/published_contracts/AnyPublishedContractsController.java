
package acme.features.any.published_contracts;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.contract.Contract;

@Controller
public class AnyPublishedContractsController extends AbstractController<Any, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyPublishedContractsListService	listService;

	@Autowired
	private AnyPublishedContractsShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
