
package acme.features.sponsor.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("select count(s) from Invoice s where s.sponsorship.sponsor.id = :sid and s.tax <= 21")
	Integer getTaxUnder21(int sid);

	@Query("select count(s) from Sponsorship s where s.sponsor.id = :sid and s.link is not null")
	Integer getLinkedSponsorships(int sid);

	@Query("select s.amount.currency, avg(s.amount.amount) from Sponsorship s where s.sponsor.id = :sid group by s.amount.currency")
	List<Object[]> avgSponsorshipAmount(int sid);

	@Query("select s.amount.currency, stddev(s.amount.amount) from Sponsorship s where s.sponsor.id = :sid group by s.amount.currency")
	List<Object[]> stdSponsorshipAmount(int sid);

	@Query("select s.amount.currency, min(s.amount.amount) from Sponsorship s where s.sponsor.id = :sid group by s.amount.currency")
	List<Object[]> minSponsorshipAmount(int sid);

	@Query("select s.amount.currency, max(s.amount.amount) from Sponsorship s where s.sponsor.id = :sid group by s.amount.currency")
	List<Object[]> maxSponsorshipAmount(int sid);

	@Query("select s.quantity.currency, avg(s.quantity.amount) from Invoice s where s.sponsorship.sponsor.id = :sid group by s.quantity.currency")
	List<Object[]> avgInvoiceAmount(int sid);

	@Query("select s.quantity.currency, stddev(s.quantity.amount) from Invoice s where s.sponsorship.sponsor.id = :sid group by s.quantity.currency")
	List<Object[]> stdInvoiceAmount(int sid);

	@Query("select s.quantity.currency, min(s.quantity.amount) from Invoice s where s.sponsorship.sponsor.id = :sid group by s.quantity.currency")
	List<Object[]> minInvoiceAmount(int sid);

	@Query("select s.quantity.currency, max(s.quantity.amount) from Invoice s where s.sponsorship.sponsor.id = :sid group by s.quantity.currency")
	List<Object[]> maxInvoiceAmount(int sid);

}
