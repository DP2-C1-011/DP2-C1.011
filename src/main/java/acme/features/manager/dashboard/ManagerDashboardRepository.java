
package acme.features.manager.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.project.UsPriority;
import acme.entities.project.UserStory;

/*
 * The system must handle manager dashboards with the following data: total number of “must”, “should”, “could”, and “won’t”
 * user stories; average, deviation, minimum, and maximum estimated
 * cost of the user stories; average, deviation, minimum, and maximum cost of the projects.
 */
@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	//task
	@Query("select count(u) from UserStory u where u.priority = :priority and u.draftMode =false")
	Integer countUSbyPriority(UsPriority priority);

	//UserStory
	@Query("select u from UserStory u where  u.manager.id=:id")
	Collection<UserStory> findAllUserStories(int id);

	@Query("select avg(u.estimatedCost) from UserStory u where  u.manager.id = :id and u.draftMode =false")
	Double averageEstimationUserStories(int id);

	@Query("select STDDEV(u.estimatedCost) from UserStory u where u.manager.id = :id and u.draftMode =false")
	Double deviationEstimationUserStories(int id);

	@Query("select min(u.estimatedCost)from UserStory u where u.manager.id = :id and u.draftMode =false")
	Double minEstimationUserStories(int id);

	@Query("select max(u.estimatedCost) from UserStory u where u.manager.id = :id and u.draftMode =false")
	Double maxEstimationUserStories(int id);

	//Project

	@Query("select p from Project p where  p.manager.id =:id")
	Collection<Project> findAllProjects(int id);

	@Query("select avg(p.cost.amount) from Project p where  p.manager.id = :id and p.draftMode =false")
	Double averageProjectCost(int id);

	@Query("select STDDEV(p.cost.amount) from Project p where p.manager.id = :id and p.draftMode =false")
	Double deviationProjectCost(int id);

	@Query("select min(p.cost.amount)from Project p where p.manager.id = :id and p.draftMode =false")
	Double minProjectCost(int id);

	@Query("select max(p.cost.amount) from Project p where p.manager.id = :id and p.draftMode =false")
	Double maxProjectCost(int id);

	@Query("select p from Project p where p.manager.id = :managerId and p.draftMode =false")
	Collection<Project> findManyProjectsByManagerId(int managerId);

}
