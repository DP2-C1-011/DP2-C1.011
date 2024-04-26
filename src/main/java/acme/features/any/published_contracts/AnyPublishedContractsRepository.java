
package acme.features.any.published_contracts;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;

@Repository
public interface AnyPublishedContractsRepository extends AbstractRepository {

	@Query("select c from Contract c where c.draftMode = false")
	Collection<Contract> findAllPublishedContracts();

	@Query("select c from Contract c where c.id = :id")
	Contract findContractById(int id);
}
