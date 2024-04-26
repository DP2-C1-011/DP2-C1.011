
package acme.features.auditor.code_audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audit.CodeAudit;
import acme.entities.audit.CodeAuditType;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditListService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<CodeAudit> objects;
		int auditorId;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findManyCodeAuditsByAuditorId(auditorId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Dataset dataset;

		//NO SÉ SI IRÍA AQUÍ TBN EL ATRIBUTO MARK, QUE ES TRANSIENT Y SE CALCULA EN EL SERVICIO
		dataset = super.unbind(object, "code", "executionDate", "codeAuditType", "correctiveActions", "optionalLink", "draftMode");
		SelectChoices choices;
		choices = SelectChoices.from(CodeAuditType.class, object.getCodeAuditType());
		dataset.put("codeAuditTypes", choices);
		super.getResponse().addData(dataset);
	}

}
