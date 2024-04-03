
package acme.features.developer.training_session;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.training.TrainingSession;

@Repository
public interface DeveloperTrainingSessionRepository extends AbstractRepository {

	@Query("select ts from TrainingSession ts where ts.id = :id")
	TrainingSession findTrainingSessionById(int id);

	@Query("select ts from TrainingSession ts where ts.trainingModule.developer.id = :id")
	Collection<TrainingSession> findTrainingModuleByDeveloperId(int id);

}
