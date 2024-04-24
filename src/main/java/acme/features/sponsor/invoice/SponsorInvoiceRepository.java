
package acme.features.sponsor.invoice;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsor.Invoice;
import acme.entities.sponsor.Sponsorship;
import acme.roles.Sponsor;

@Repository
public interface SponsorInvoiceRepository extends AbstractRepository {

	@Query("select p from Invoice p where p.id = :id")
	Invoice findInvoiceById(int id);

	@Query("select p from Invoice p")
	Collection<Invoice> findAllInvoice();

	@Query("select p from Invoice p where p.sponsorship.sponsor.id = :id")
	Collection<Invoice> findInvoiceBySponsor(int id);

	@Query("select p from Invoice p where p.sponsorship.id = :id")
	Collection<Invoice> findInvoiceBySponsorship(int id);

	@Query("select p.sponsorship.sponsor from Invoice p where p.sponsorship.sponsor.id = :id")
	Sponsor findOneSponsorById(int id);

	@Query("select p.sponsorship from Invoice p where p.sponsorship.id = :id")
	Sponsorship findOneSponsorshipById(int id);

	@Query("select p from Invoice p where p.code = :code")
	Invoice findOneInvoiceByCode(String code);

}
