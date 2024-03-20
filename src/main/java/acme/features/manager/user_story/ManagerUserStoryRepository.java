
package acme.features.manager.user_story;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.UserStory;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("select u from UserStory u where u.id = :id")
	UserStory findUserStoryById(int id);

	@Query("select u from UserStory u")
	Collection<UserStory> findAllUserStory();

	@Query("select u from UserStory u where u.project.manager.id = :id")
	Collection<UserStory> findUserStoryByManagerId(int id);

}
