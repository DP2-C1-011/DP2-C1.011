
package acme.features.auditor.audit_record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audit.AuditRecord;
import acme.entities.audit.CodeAudit;
import acme.entities.audit.Mark;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordDeleteService extends AbstractService<Auditor, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditRecordRepository auditorAuditRecordRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int auditRecordId;
		Auditor auditor;
		AuditRecord auditRecord;

		auditRecordId = super.getRequest().getData("id", int.class);
		auditRecord = this.auditorAuditRecordRepository.findOneAuditRecordById(auditRecordId);
		auditor = auditRecord.getCodeAudit().getAuditor();

		status = auditRecord != null && super.getRequest().getPrincipal().hasRole(auditor) && auditRecord.getCodeAudit().getAuditor().equals(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.auditorAuditRecordRepository.findOneAuditRecordById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		CodeAudit codeAudit;
		codeAudit = object.getCodeAudit();

		super.bind(object, "code", "periodStart", "periodEnd", "mark", "optionalLink");
		object.setCodeAudit(codeAudit);
	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("*"))
			super.state(object.getDraftMode(), "*", "auditor.audit-record.error.publish");

	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;
		this.auditorAuditRecordRepository.delete(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		CodeAudit codeAudit;
		codeAudit = object.getCodeAudit();
		SelectChoices choices;

		choices = SelectChoices.from(Mark.class, object.getMark());

		Dataset dataset;
		dataset = super.unbind(object, "code", "periodStart", "mark", "periodEnd", "optionalLink", "draftMode");
		dataset.put("codeAudit", codeAudit);
		dataset.put("mark", choices);
		super.getResponse().addData(dataset);
	}
}
