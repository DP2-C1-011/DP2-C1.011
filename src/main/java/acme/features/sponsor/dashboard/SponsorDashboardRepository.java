
package acme.features.sponsor.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.datatypes.Money;
import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("select count(s) from Invoice s where s.sponsorship.sponsor.id = :sid and s.tax <= 21. ")
	Integer getTaxUnder21(int sid);

	@Query("select count(s) from Sponsorship s where s.sponsor.id = :sid and s.optionalLink is not null")
	Integer getLinkedSponsorships(int sid);

	@Query("select avg(s.amount) from Sponsorship s where s.sponsor.id = :sid")
	Money avgSponsorshipAmount(int sid);

	@Query("select stddev(s.amount) from Sponsorship s where s.sponsor.id = :sid")
	Money stdSponsorshipAmount(int sid);

	@Query("select min(s.amount) from Sponsorship s where s.sponsor.id = :sid")
	Money minSponsorshipAmount(int sid);

	@Query("select max(s.amount) from Sponsorship s where s.sponsor.id = :sid")
	Money maxSponsorshipAmount(int sid);

	@Query("select avg(s.amount) from Invoice s where s.sponsorship.sponsor.id = :sid")
	Money avgInvoiceAmount(int sid);

	@Query("select stddev(s.amount) from Invoice s where s.sponsorship.sponsor.id = :sid")
	Money stdInvoiceAmount(int sid);

	@Query("select min(s.amount) from Invoice s where s.sponsorship.sponsor.id = :sid")
	Money minInvoiceAmount(int sid);

	@Query("select max(s.amount) from Invoice s where s.sponsorship.sponsor.id = :sid")
	Money maxInvoiceAmount(int sid);
}
