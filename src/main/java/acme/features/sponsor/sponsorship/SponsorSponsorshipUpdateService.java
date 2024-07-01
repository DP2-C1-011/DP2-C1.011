
package acme.features.sponsor.sponsorship;

import java.time.temporal.ChronoUnit;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.MoneyService;
import acme.entities.project.Project;
import acme.entities.sponsor.Method;
import acme.entities.sponsor.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipUpdateService extends AbstractService<Sponsor, Sponsorship> {

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

		int projectId;
		Project project;
		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "moment", "startDate", "endDate", "amount", "financial", "email", "link");
		object.setProject(project);
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(object.getProject().getDraftMode().equals(false), "project", "sponsor.sponsorship.form.error.not-published-project");

		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(object.getDraftMode(), "draftMode", "sponsor.sponsorship.form.error.draftMode");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship existing;

			existing = this.repository.findSponsorshipByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "sponsor.sponsorship.form.error.duplicateCode");
		}

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

		Collection<Project> projects;
		SelectChoices projectChoices;

		projects = this.repository.findAllPublishedProjects();
		projectChoices = SelectChoices.from(projects, "code", object.getProject());

		dataset.put("project", projectChoices.getSelected().getKey());
		dataset.put("projects", projectChoices);

		super.getResponse().addGlobal("sponsorshipId", object.getId());
		super.getResponse().addData(dataset);
	}
}
