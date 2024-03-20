
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;

@Repository
public interface ClientContractRepository extends AbstractRepository {

	@Query("select c from Contract c where c.id = :id")
	Contract findContractById(int id);

	@Query("select c from Contract c")
	Collection<Contract> findAllContracts();

	@Query("select c from Contract c where c.client.id = :id")
	Collection<Contract> findContractsByClientId(int id);

}
