
package acme.features.client.participates_in;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.project.ParticipatesIn;
import acme.roles.Client;

@Controller
public class ClientParticipatesInController extends AbstractController<Client, ParticipatesIn> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientParticipatesInListService		listService;

	@Autowired
	private ClientParticipatesInShowService		showService;

	@Autowired
	private ClientParticipatesInCreateService	createService;

	@Autowired
	private ClientParticipatesInDeleteService	deleteService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
	}

}
