
package acme.features.manager.user_story;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.project.UserStory;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("select u from UserStory u where u.id = :id")
	UserStory findUserStoryById(int id);

	@Query("select u from UserStory u")
	Collection<UserStory> findAllUserStory();

	@Query("select u from UserStory u where u.project.manager.id = :id")
	Collection<UserStory> findUserStoryByManagerId(int id);

	@Query("select u.project from UserStory u where u.project.manager.id = :id")
	Project findProjectByManagerId(int id);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select u from UserStory u where u.project.id = :id")
	Collection<UserStory> findUserStoryByProjectId(int id);
}
