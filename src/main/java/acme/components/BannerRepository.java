
package acme.components;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.helpers.MomentHelper;
import acme.client.helpers.RandomHelper;
import acme.client.repositories.AbstractRepository;
import acme.entities.Banner;

@Repository
public interface BannerRepository extends AbstractRepository {

	@Query("select count(b) from Banner b where b.displayPeriodStart <= :date and b.displayPeriodEnd > :date")
	int countBanner(Date date);

	@Query("select b from Banner b where b.displayPeriodStart <= :date and b.displayPeriodEnd > :date")
	List<Banner> findManyBanners(Date date);

	default Banner findRandomBanner() {
		Banner result;
		int count, index;
		List<Banner> list;

		count = this.countBanner(MomentHelper.getCurrentMoment());
		if (count == 0)
			result = null;
		else {
			index = RandomHelper.nextInt(0, count);
			list = this.findManyBanners(MomentHelper.getCurrentMoment());
			result = list.isEmpty() ? null : list.get(index);
		}

		return result;
	}

}
