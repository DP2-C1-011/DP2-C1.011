
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
public class AuditorCodeAuditCreateService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Auditor.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudit object;
		Auditor auditor;
		int auditorId;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		auditor = this.repository.findAuditorById(auditorId);

		object = new CodeAudit();
		object.setDraftMode(true);
		object.setAuditor(auditor);

		super.getBuffer().addData(object);

	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		super.bind(object, "code", "executionDate", "codeAuditType", "correctiveActions", "optionalLink");

	}

	//Creo que aquí iría la restricción de la nota //mark
	@Override
	public void validate(final CodeAudit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudit existing;
			existing = this.repository.findCodeAuditByCode(object.getCode());
			super.state(existing == null, "code", "auditor.audit.form.error.duplicateCode");
		}
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "executionDate", "codeAuditType", "correctiveActions", "optionalLink", "draftMode");
		SelectChoices choices;

		choices = SelectChoices.from(CodeAuditType.class, object.getCodeAuditType());
		dataset.put("codeAuditTypes", choices);
		super.getResponse().addData(dataset);
	}

}
