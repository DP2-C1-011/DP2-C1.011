
package acme.features.auditor.code_audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audit.CodeAudit;
import acme.entities.audit.CodeAuditType;
import acme.entities.project.Project;
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
		Auditor auditor;

		id = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditById(id);
		auditor = codeAudit == null ? null : codeAudit.getAuditor();

		status = super.getRequest().getPrincipal().hasRole(auditor) || codeAudit != null && !codeAudit.isDraftMode();

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {

		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCodeAuditById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final CodeAudit object) {

		assert object != null;

		Collection<Project> projects;
		SelectChoices projectsChoices;
		SelectChoices codeAuditTypesChoices;
		Dataset dataset;

		projects = this.repository.findAllProjects();
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "executionDate", "codeAuditType", "correctiveActions", "optionalLink", "draftMode");
		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);

		codeAuditTypesChoices = SelectChoices.from(CodeAuditType.class, object.getCodeAuditType());
		dataset.put("codeAuditTypes", codeAuditTypesChoices);
		super.getResponse().addData(dataset);
	}

}
