
package acme.features.auditor.audit_record;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

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
public class AuditorAuditRecordUpdateService extends AbstractService<Auditor, AuditRecord> {

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
	public void bind(final AuditRecord object) {
		assert object != null;

		CodeAudit codeAudit;
		codeAudit = object.getCodeAudit();

		super.bind(object, "code", "periodStart", "periodEnd", "mark", "optionalLink");
		object.setCodeAudit(codeAudit);
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
	public void validate(final AuditRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("periodEnd")) {
			long diffInMili;
			long diffInHour;

			super.state(object.getPeriodEnd().after(Date.valueOf("2000-1-1")) || object.getPeriodEnd().equals(Date.valueOf("2000-1-1")), "periodEnd", "auditor.code-audit.error.executionDate");

			if (object.getPeriodStart() != null) {
				diffInMili = object.getPeriodEnd().getTime() - object.getPeriodStart().getTime();
				diffInHour = TimeUnit.MILLISECONDS.toHours(diffInMili);
				super.state(diffInHour >= 1, "periodEnd", "auditor.audit-record.error.duration");
				super.state(object.getPeriodStart() != null || object.getPeriodStart().before(object.getPeriodEnd()), "periodEnd", "auditor.audit-record.error.consecutiveDates");
			}
		}

		if (!super.getBuffer().getErrors().hasErrors("periodStart"))
			super.state(object.getPeriodStart().after(Date.valueOf("2000-1-1")) || object.getPeriodStart().equals(Date.valueOf("2000-1-1")), "periodStart", "auditor.code-audit.error.executionDate");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecord existing;
			boolean status;

			existing = this.auditorAuditRecordRepository.findOneAuditRecordByCode(object.getCode());
			if (existing != null)
				status = existing.getId() == object.getId();
			else
				status = false;
			super.state(existing == null || status, "code", "auditor.audit-record.error.code");
		}

		if (!super.getBuffer().getErrors().hasErrors("*"))
			super.state(object.getDraftMode(), "*", "auditor.audit-record.error.publish");
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;

		this.auditorAuditRecordRepository.save(object);
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
