
package acme.features.auditor.code_audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit.CodeAudit;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("select au from CodeAudit au")
	Collection<CodeAudit> findAllCodeAudits();

	@Query("select au from CodeAudit au where au.id = :id")
	CodeAudit findCodeAuditById(int id);

	@Query("select au from CodeAudit au where au.auditor.id = :id")
	Collection<CodeAudit> findCodeAuditByAuditorId(int id);

	@Query("select a from Auditor a where a.id = :id")
	Auditor findAuditorById(int id);

	@Query("select au from CodeAudit au where au.code = :code")
	CodeAudit findCodeAuditByCode(String code);

}
