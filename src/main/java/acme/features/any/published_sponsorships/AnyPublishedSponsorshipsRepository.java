
package acme.features.any.published_sponsorships;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsor.Sponsorship;

@Repository
public interface AnyPublishedSponsorshipsRepository extends AbstractRepository {

	@Query("select c from Sponsorship c where c.draftMode = false")
	Collection<Sponsorship> findAllPublishedSponsorships();

	@Query("select c from Sponsorship c where c.id = :id")
	Sponsorship findSponsorshipById(int id);

}
