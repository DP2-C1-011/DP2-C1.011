
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
public class AuditorCodeAuditPublishService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int auditorId;
		CodeAudit codeAudit;
		Auditor auditor;

		auditorId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditById(auditorId);
		auditor = codeAudit == null ? null : codeAudit.getAuditor();
		status = codeAudit != null && codeAudit.isDraftMode() && super.getRequest().getPrincipal().hasRole(auditor);

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
	public void bind(final CodeAudit object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);

		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "executionDate", "codeAuditType", "correctiveActions", "optionalLink");

		object.setProject(project);
	}

	//Creo que aquí iría la restricción de la nota //mark
	@Override
	public void validate(final CodeAudit object) {
		assert object != null;
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices projectsChoices;
		Dataset dataset;
		SelectChoices codeAuditTypesChoices;

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
