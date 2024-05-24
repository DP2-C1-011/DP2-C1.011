
package acme.features.sponsor.sponsorship;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.MoneyService;
import acme.entities.sponsor.Method;
import acme.entities.sponsor.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipPublishService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	SponsorSponsorshipRepository	repository;
	@Autowired
	MoneyService					moneyService;


	@Override
	public void authorise() {
		boolean status;
		int sponsorshipId;
		Sponsorship sponsorship;
		Sponsor sponsor;

		sponsorshipId = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findSponsorshipById(sponsorshipId);
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();
		status = sponsorship != null && sponsorship.getDraftMode() && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Sponsorship object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findSponsorshipById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;
		super.bind(object, "code", "moment", "startDate", "endDate", "amount", "financial", "email", "link");
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;
		int sponsorshipId;
		sponsorshipId = super.getRequest().getData("id", int.class);

		Double totalInvoices = this.repository.sumInvoicesBySponsorshipId(sponsorshipId);

		if (totalInvoices == null)
			totalInvoices = 0.;

		Double totalSponsorship = this.repository.findSponsorshipAmountById(sponsorshipId);

		if (!super.getBuffer().getErrors().hasErrors("*"))
			super.state(totalInvoices >= totalSponsorship, "*", "sponsor.sponsorship.form.error.invoice-amount");

		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			Boolean currencyState = this.moneyService.checkContains(object.getAmount().getCurrency());
			super.state(currencyState, "amount", "sponsor.sponsorship.form.error.invalid-currency");
		}

		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			Boolean currencyState = object.getAmount().getAmount() > 0.00;
			super.state(currencyState, "amount", "sponsor.sponsorship.form.error.negative-amount");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate") && !super.getBuffer().getErrors().hasErrors("startDate") && object.getEndDate() != null) {
			super.state(MomentHelper.isAfter(object.getEndDate(), object.getStartDate()), "endDate", "sponsor.sponsorship.form.error.finishBeforeStart");
			super.state(MomentHelper.isAfter(object.getEndDate(), MomentHelper.deltaFromMoment(object.getStartDate(), 30, ChronoUnit.DAYS)), "endDate", "sponsor.sponsorship.form.error.periodTooShort");
		}

	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;
		Dataset dataset;
		SelectChoices choices;

		choices = SelectChoices.from(Method.class, object.getFinancial());

		dataset = super.unbind(object, "code", "moment", "startDate", "endDate", "amount", "financial", "email", "link", "draftMode");

		dataset.put("methods", choices);
		super.getResponse().addGlobal("sponsorshipId", object.getId());
		super.getResponse().addData(dataset);
	}

}
