
package acme.features.auditor.code_audit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.audit.CodeAudit;
import acme.roles.Auditor;

@Controller
public class AuditorCodeAuditController extends AbstractController<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditListService		listService;

	@Autowired
	private AuditorCodeAuditShowService		showService;

	@Autowired
	private AuditorCodeAuditCreateService	createService;
	/*
	 * @Autowired
	 * protected AuditorCodeAuditDeleteService deleteService;
	 * 
	 * @Autowired
	 * protected AuditorCodeAuditUpdateService updateService;
	 * 
	 * @Autowired
	 * protected AuditorCodeAuditPublishService publishService;
	 */

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);

	}

}
