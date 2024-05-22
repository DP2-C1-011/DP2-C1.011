
package acme.features.manager.projectUserstory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.project.ProjectUserStory;
import acme.entities.project.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerProjectUserStoryRepository extends AbstractRepository {

	@Query("select p from Project p where p.manager.id= :managerId")
	Collection<Project> findProjectsByManagerId(int managerId);

	@Query("select p from Project p where p.manager.id= :managerId and p.draftMode =true")
	Collection<Project> findProjectsByManagerIdAndNonPublished(int managerId);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);
	@Query("select us from UserStory us where us.id = :id")
	UserStory findUserStoryById(int id);
	@Query("select pus from ProjectUserStory pus where pus.id = :id")
	ProjectUserStory findLinkById(int id);
	@Query("SELECT us FROM UserStory us " + "WHERE us.manager.userAccount.id = :managerId " + "AND us NOT IN (SELECT pus.userStory FROM ProjectUserStory pus WHERE pus.project.id = :userStoryId)")
	Collection<UserStory> findUserStoriesByManagerIdAndNotInProject(int managerId, int userStoryId);
	@Query("select pus from ProjectUserStory pus where pus.project.id = :id")
	Collection<ProjectUserStory> findLinksByProjectId(int id);

	@Query("select l from UserStory l inner join ProjectUserStory cl on l = cl.userStory inner join Project c on cl.project = c where c.id = :id")
	Collection<UserStory> findUserStoryByProject(int id);

	@Query("select l from Manager l where l.id = :id")
	Manager findOneManagerById(int id);

	@Query("select us.manager from UserStory us where us.id = :id")
	Manager findOneManagerByUserStoryId(int id);

	@Query("select l from UserStory l where l.id = :id")
	UserStory findOneUserStoryById(int id);

	@Query("select c from Project c where c.manager = :manager and c.draftMode = true")
	Collection<Project> findNonPublishedProjectsByManager(Manager manager);

	@Query("select c from Project c inner join ProjectUserStory cl on c = cl.project inner join UserStory l on cl.userStory = l where l = :userStory and c.draftMode =true")
	Collection<Project> findProjectByUserStory(UserStory userStory);

	@Query("select cl from ProjectUserStory cl where cl.project = :project and cl.userStory = :userStory")
	ProjectUserStory findProjectUserStoryByProjectUserStory(Project project, UserStory userStory);

	@Query("SELECT p FROM Project p " + "WHERE p.manager.userAccount.id = :managerId " + "AND p NOT IN (SELECT pus.project FROM ProjectUserStory pus WHERE pus.userStory.id = :userStoryId)")
	Collection<Project> findProjectsByManagerIdAndNotAssociatedWithUserStory(int managerId, int userStoryId);

	@Query("select u.project from ProjectUserStory u where u.userStory.id = :userStoryId")
	Collection<Project> findProjectsByManagerIdAndNonPublishedAndByUserStory(int userStoryId);

	@Query("select p from Project p where p.manager.id = :id")
	Collection<Project> findProjectByManager(int id);

}
