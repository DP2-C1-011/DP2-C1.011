
package acme.features.any.published_training_module;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.training.TrainingModule;

@Repository
public interface AnyPublishedTrainingModuleRepository extends AbstractRepository {

	@Query("select tm from TrainingModule tm where tm.draftMode = false")
	Collection<TrainingModule> findAllPublishedTrainingModule();

	@Query("select tm from TrainingModule tm where tm.id = :id")
	TrainingModule findTrainingModuleById(int id);
}
