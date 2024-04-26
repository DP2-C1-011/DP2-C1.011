
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.Banner;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	@Autowired
	AdministratorBannerRepository repository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Administrator.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Banner object;
		object = new Banner();
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "displayPeriodStart", "displayPeriodEnd", "linkPicture", "slogan", "linkDoc");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("instantiationMoment") && !super.getBuffer().getErrors().hasErrors("displayPeriodStart"))
			super.state(MomentHelper.isAfter(object.getDisplayPeriodStart(), object.getInstantiationMoment()), "displayPeriodStart", "administrator.banner.form.error.startBeforeInstantiate");

		if (!super.getBuffer().getErrors().hasErrors("displayPeriodStart") && !super.getBuffer().getErrors().hasErrors("displayPeriodEnd")) {
			super.state(MomentHelper.isAfter(object.getDisplayPeriodEnd(), object.getDisplayPeriodStart()), "displayPeriodEnd", "administrator.banner.form.error.startBeforeEnd");
			super.state(MomentHelper.isAfter(object.getDisplayPeriodEnd(), MomentHelper.deltaFromMoment(object.getDisplayPeriodStart(), 7, ChronoUnit.DAYS)), "displayPeriodEnd", "administrator.banner.form.error.periodTooShort");
		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "instantiationMoment", "displayPeriodStart", "displayPeriodEnd", "linkPicture", "slogan", "linkDoc");
		super.getResponse().addData(dataset);
	}
}
