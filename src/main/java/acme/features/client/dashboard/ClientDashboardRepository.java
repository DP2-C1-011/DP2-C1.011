
package acme.features.client.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;

@Repository
public interface ClientDashboardRepository extends AbstractRepository {

	//	@Query("select avg(sum(c.systemCurrencyBudget.amount)) from Contract c where c.client.id = :clientId")
	//	Double averageBudgetOfContractsPerClient(int clientId);
	//
	//	@Query("select max(c.systemCurrencyBudget.amount) from Contract c where c.client.id = :clientId")
	//	Double maximumBudgetOfContractsPerClient(int clientId);
	//
	//	@Query("select min(c.systemCurrencyBudget.amount) from Contract c where c.client.id = :clientId")
	//	Double minimumBudgetOfContractsPerClient(int clientId);

	//	@Query("select sqrt((select sum((c.systemCurrencyBudget.amount - :avg) * (c.systemCurrencyBudget.amount - :avg)) from Contract c where c.client.id = :clientId) / count(d)) from Contract d where d.client.id = :clientId")
	//	Double deviationBudgetOfContractsPerClient(Double avg, int clientId);

	@Query("select count(p) from ProgressLog p where p.contract.client.id = :clientId and p.completeness > 25 and p.completeness <= 50")
	Integer totalNumberOfProgressLogsCompletenessBetween25And50(int clientId);

	@Query("select count(p) from ProgressLog p where p.contract.client.id = :clientId and p.completeness <= 25")
	Integer totalNumberOfProgressLogsCompletenessBelow25(int clientId);

	@Query("select count(p) from ProgressLog p where p.contract.client.id = :clientId and p.completeness > 50 and p.completeness <= 75")
	Integer totalNumberOfProgressLogsCompletenessBetween50And75(int clientId);

	@Query("select count(p) from ProgressLog p where p.contract.client.id = :clientId and p.completeness > 75")
	Integer totalNumberOfProgressLogsCompletenessAbove75(int clientId);

	@Query("select c from Contract c where c.client.id = :clientId")
	Collection<Contract> findManyContractsByClientId(int clientId);

}
