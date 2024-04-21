
package acme.features.manager.project_user_story;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.project.ProjectUserStory;
import acme.entities.project.UserStory;

@Repository
public interface ManagerProjectUserStoryRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);
	@Query("select us from UserStory us where us.id = :id")
	UserStory findUserStoryById(int id);
	@Query("select pus from ProjectUserStory pus where pus.id = :id")
	ProjectUserStory findLinkById(int id);
	@Query("select us from UserStory us where us.project.manager.userAccount.id = :id")
	Collection<UserStory> findUserStoriesByManagerId(int id);
	@Query("select pus from ProjectUserStory pus where pus.project.id = :id")
	Collection<ProjectUserStory> findLinksByProjectId(int id);
}
