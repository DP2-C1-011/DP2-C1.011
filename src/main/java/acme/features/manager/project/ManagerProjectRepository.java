
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit.AuditRecord;
import acme.entities.audit.CodeAudit;
import acme.entities.project.ParticipatesIn;
import acme.entities.project.Project;
import acme.entities.project.ProjectUserStory;
import acme.roles.Manager;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select p from Project p where p.manager.id = :id")
	Collection<Project> findProjectByManagerId(int id);

	@Query("select m from Manager m where m.id = :id")
	Manager findOneManagerById(int id);

	@Query("select p from Project p where p.code = :code")
	Project findOneProjectByCode(String code);

	@Query("select u from ProjectUserStory u where u.project.id = :id")
	Collection<ProjectUserStory> findProjectUserStoryByProjectId(int id);

	@Query("select p from ParticipatesIn p where p.project.id = :id")
	Collection<ParticipatesIn> findParticipatesInByProjectId(int id);

	@Query("select p from CodeAudit p where p.project.id = :id")
	Collection<CodeAudit> findCodeAuditByProjectId(int id);

	@Query("select ar from AuditRecord ar where ar.codeAudit.id = :codeAuditId")
	Collection<AuditRecord> findManyAuditRecordsByCodeAuditId(int codeAuditId);

}
