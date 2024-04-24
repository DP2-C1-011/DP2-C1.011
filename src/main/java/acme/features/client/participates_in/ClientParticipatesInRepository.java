
package acme.features.client.participates_in;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.project.ParticipatesIn;
import acme.entities.project.Project;
import acme.roles.Client;

@Repository
public interface ClientParticipatesInRepository extends AbstractRepository {

	@Query("select pi from ParticipatesIn pi where pi.id = :id")
	ParticipatesIn findParticipatesInById(int id);

	@Query("select pi from ParticipatesIn pi where pi.client.id = :clientId and pi.project.id = :projectId")
	ParticipatesIn findOneParticipatesInByClientAndProject(int clientId, int projectId);

	@Query("select pi from ParticipatesIn pi where pi.code = :code")
	ParticipatesIn findOneParticipatesInByCode(String code);

	@Query("select pi from ParticipatesIn pi where pi.client.id = :clientId")
	Collection<ParticipatesIn> findManyParticipatesInByClientId(int clientId);

	@Query("select c from Client c where c.id = :id")
	Client findOneClientById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select c from Contract c where c.code = :code")
	Contract findOneContractByCode(String code);

	@Query("select pi.project from ParticipatesIn pi where pi.client.id = :clientId")
	Collection<Project> findManyProjectsByClientId(int clientId);

	@Query("select c.project from Contract c where c.client.id = :clientId")
	Collection<Project> findManyProjectsByContractsClientId(int clientId);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

}
