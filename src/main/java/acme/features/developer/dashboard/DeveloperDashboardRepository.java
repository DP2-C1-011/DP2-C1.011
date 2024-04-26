
package acme.features.developer.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface DeveloperDashboardRepository extends AbstractRepository {

	@Query("select count(tm) from TrainingModule tm where tm.developer.id = :developerId and tm.updateMoment is not null and tm.draftMode = false")
	Integer numberOfTrainingModulesUpdated(int developerId);

	@Query("select count(ts) from TrainingSession ts where ts.trainingModule.developer.id = :developerId and ts.optionalLink is not null and ts.draftMode = false")
	Integer numberOfTrainingSessionsWithLink(int developerId);

	@Query("select avg(tm.totalTime) from TrainingModule tm where tm.developer.id = :developerId and tm.draftMode = false")
	Double avgTrainingModuleTime(int developerId);

	@Query("select stddev(tm.totalTime) from TrainingModule tm where tm.developer.id = :developerId and tm.draftMode = false")
	Double stdTrainingModuleTime(int developerId);

	@Query("select min(tm.totalTime) from TrainingModule tm where tm.developer.id = :developerId and tm.draftMode = false")
	Integer minTrainingModuleTime(int developerId);

	@Query("select max(tm.totalTime) from TrainingModule tm where tm.developer.id = :developerId and tm.draftMode = false")
	Integer maxTrainingModuleTime(int developerId);
}
