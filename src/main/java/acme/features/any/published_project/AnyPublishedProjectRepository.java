
package acme.features.any.published_project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;

@Repository
public interface AnyPublishedProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findAllPublishedProjects();

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

}
