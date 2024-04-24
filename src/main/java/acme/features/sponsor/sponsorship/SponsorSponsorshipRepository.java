
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.datatypes.Money;
import acme.client.repositories.AbstractRepository;
import acme.entities.sponsor.Invoice;
import acme.entities.sponsor.Sponsorship;
import acme.roles.Sponsor;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.id = :id")
	Sponsorship findSponsorshipById(int id);

	@Query("select s from Sponsorship s")
	Collection<Sponsorship> findAllSponsorship();

	@Query("select s from Sponsorship s where s.sponsor.id = :id")
	Collection<Sponsorship> findSponsorshipBySponsorId(int id);

	@Query("select s from Sponsor s where s.id = :id")
	Sponsor findSponsorById(int id);

	@Query("select i from Invoice i where i.sponsorship.id = :id")
	Collection<Invoice> findInvoicesBySponsorshipId(int id);

	@Query("select sum(i.quantity) from Invoice i where i.sponsorship.id = :id")
	Money sumInvoicesBySponsorshipId(int id);

	@Query("select s.amount from Sponsorship s where s.id = :id")
	Money findSponsorshipAmountById(int id);

	@Query("select s from Sponsorship s where s.code = :code")
	Sponsorship findSponsorshipByCode(String code);
}
