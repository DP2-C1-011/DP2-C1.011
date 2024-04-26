/*
 * EmployerDutyRepository.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.auditor.audit_record;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit.AuditRecord;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRecordRepository extends AbstractRepository {

	@Query("select ar from AuditRecord ar where ar.id = :id")
	AuditRecord findOneAuditRecordById(int id);

	@Query("select ar from AuditRecord ar where ar.codeAudit.id = :masterId")
	Collection<AuditRecord> findManyAuditRecordsByMasterId(int masterId);

	@Query("select a from AuditRecord a where a.codeAudit.auditor.id = :auditorId")
	Collection<AuditRecord> findAllByAuditorId(int auditorId);

	@Query("select a from AuditRecord a where a.codeAudit.id = :codeAuditId")
	Collection<AuditRecord> findAllAuditRecordsByCodeAuditId(int codeAuditId);

	@Query("select a from Auditor a where a.id = :auditorId")
	Auditor findAuditorByAuditorId(int auditorId);

	@Query("select ar from AuditRecord ar where ar.code = :code")
	AuditRecord findOneAuditRecordByCode(String code);

}
