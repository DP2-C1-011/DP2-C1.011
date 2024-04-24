
package acme.features.sponsor.dashboard;

import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("select count(s) from Invoice s where s.sponsorship.sponsor.id = :sid and s.tax <= 21. ")
	Integer getTaxUnder21(int sid);

	@Query("select count(s) from Sponsorship s where s.sponsor.id = :sid and s.link != null")
	Integer getLinkedSponsorships(int sid);

	@Query("select avg(s.amount.amount) from Sponsorship s where s.sponsor.id = :sid group by s.amount.currency")
	Map<String, Double> avgSponsorshipAmount(int sid);

	@Query("select stddev(s.amount.amount) from Sponsorship s where s.sponsor.id = :sid group by s.amount.currency")
	Map<String, Double> stdSponsorshipAmount(int sid);

	@Query("select min(s.amount.amount) from Sponsorship s where s.sponsor.id = :sid group by s.amount.currency")
	Map<String, Double> minSponsorshipAmount(int sid);

	@Query("select max(s.amount.amount) from Sponsorship s where s.sponsor.id = :sid group by s.amount.currency")
	Map<String, Double> maxSponsorshipAmount(int sid);

	@Query("select avg(s.quantity.amount) from Invoice s where s.sponsorship.sponsor.id = :sid group by s.quantity.currency")
	Map<String, Double> avgInvoiceAmount(int sid);

	@Query("select stddev(s.quantity.amount) from Invoice s where s.sponsorship.sponsor.id = :sid group by s.quantity.currency")
	Map<String, Double> stdInvoiceAmount(int sid);

	@Query("select min(s.quantity.amount) from Invoice s where s.sponsorship.sponsor.id = :sid group by s.quantity.currency")
	Map<String, Double> minInvoiceAmount(int sid);

	@Query("select max(s.quantity.amount) from Invoice s where s.sponsorship.sponsor.id = :sid group by s.quantity.currency")
	Map<String, Double> maxInvoiceAmount(int sid);
}
