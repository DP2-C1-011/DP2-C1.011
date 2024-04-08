
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.contract.ProgressLog;
import acme.entities.project.Project;
import acme.roles.Client;

@Repository
public interface ClientContractRepository extends AbstractRepository {

	@Query("select c from Contract c where c.id = :id")
	Contract findContractById(int id);

	@Query("select c from Contract c")
	Collection<Contract> findAllContracts();

	@Query("select c from Contract c where c.client.id = :id")
	Collection<Contract> findContractsByClientId(int id);

	@Query("select c from Client c where c.id = :id")
	Client findOneClientById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select c from Contract c where c.code = :code")
	Contract findOneContractByCode(String code);

	@Query("select pi.project from ParticipatesIn pi where pi.client.id = :clientId")
	Collection<Project> findManyProjectsByClientId(int clientId);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select sum(p.completeness) from ProgressLog p where p.contract.id = :contractId")
	Integer computeCompletenessByContractId(int contractId);

	@Query("select pl from ProgressLog pl where pl.contract.id = :contractId")
	Collection<ProgressLog> findManyProgressLogsByContractId(int contractId);

	@Query("select p from ProgressLog p where (p.contract.id = :contractId and p.draftMode = false)")
	Collection<ProgressLog> findTotalPublishedProgressLogsByContractId(int contractId);

	@Query("select sum(c.systemCurrencyBudget.amount) from Contract c where c.project.id = :projectId")
	Double computeTotalBudgetsByProject(int projectId);

}
