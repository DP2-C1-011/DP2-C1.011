
package acme.features.auditor.code_audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audit.CodeAudit;
import acme.entities.audit.CodeAuditType;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditShowService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {

		boolean status;
		int id;
		CodeAudit codeAudit;

		id = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findCodeAuditById(id);

		status = codeAudit != null && super.getRequest().getPrincipal().hasRole(Auditor.class);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {

		CodeAudit codeAudit;
		int id;

		id = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findCodeAuditById(id);

		super.getBuffer().addData(codeAudit);
	}

	@Override
	public void unbind(final CodeAudit codeAudit) {

		assert codeAudit != null;

		Dataset dataset;

		//NO SÉ SI IRÍA AQUÍ TBN EL ATRIBUTO MARK, QUE ES TRANSIENT Y SE CALCULA EN EL SERVICIO
		dataset = super.unbind(codeAudit, "code", "executionDate", "codeAuditType", "correctiveActions", "optionalLink", "draftMode");
		SelectChoices choices;
		choices = SelectChoices.from(CodeAuditType.class, codeAudit.getCodeAuditType());
		dataset.put("codeAuditTypes", choices);
		super.getResponse().addData(dataset);
	}

}
