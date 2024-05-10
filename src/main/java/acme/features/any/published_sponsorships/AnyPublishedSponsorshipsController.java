
package acme.features.any.published_sponsorships;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.sponsor.Sponsorship;

@Controller
public class AnyPublishedSponsorshipsController extends AbstractController<Any, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyPublishedSponsorshipsListService	listService;

	@Autowired
	private AnyPublishedSponsorshipsShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
