
package acme.features.any.published_progress_logs;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.contract.ProgressLog;

@Repository
public interface AnyPublishedProgressLogRepository extends AbstractRepository {

	@Query("select p from ProgressLog p where p.id = :id")
	ProgressLog findProgressLogById(int id);

	@Query("select p from ProgressLog p where p.contract.id = :id")
	Collection<ProgressLog> findProgressLogsByContractId(int id);

	@Query("select c from Contract c where c.id = :id")
	Contract findOneContractById(int id);
}
