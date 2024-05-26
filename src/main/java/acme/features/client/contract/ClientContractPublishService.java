
package acme.features.client.contract;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.MoneyService;
import acme.entities.contract.Contract;
import acme.entities.project.Project;
import acme.roles.Client;

@Service
public class ClientContractPublishService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractRepository	repository;

	@Autowired
	MoneyService						moneyService;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int contractId;
		Contract contract;
		Client client;

		contractId = super.getRequest().getData("id", int.class);
		contract = this.repository.findContractById(contractId);
		client = contract == null ? null : contract.getClient();
		status = contract != null && contract.getDraftMode() && super.getRequest().getPrincipal().hasRole(client);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Contract object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findContractById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "provider", "customer", "goals", "budget");
		object.setProject(project);
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;
		
		if (!super.getBuffer().getErrors().hasErrors("project")) {

			super.state(object.getProject().getDraftMode().equals(false),"project", "client.contract.form.error.unpublishedproject");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget")) {
			Double budget;
			budget = object.getBudget().getAmount();
			super.state(budget > 0, "budget", "client.contract.error.budget-negative");
			Project project;

			project = object.getProject();
			Double objectAmount;
			Boolean currencyState = this.moneyService.checkContains(object.getBudget().getCurrency());
			super.state(currencyState, "budget", "client.contract.form.error.budget.invalid-currency");
			if (project != null) {
				Double projectCost = this.moneyService.computeMoneyExchange(project.getCost(), "EUR").getTarget().getAmount();
				if (currencyState) {
					objectAmount = this.moneyService.computeMoneyExchange(object.getBudget(), "EUR").getTarget().getAmount();
					super.state(projectCost >= objectAmount, "budget", "client.contract.form.error.above-cost");
				}
			}
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;

			existing = this.repository.findOneContractByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "client.contract.form.error.duplicated");
		}

		Integer totalPublishedProgressLogs;
		Integer numberProgressLogsPerContract;

		numberProgressLogsPerContract = this.repository.findManyProgressLogsByContractId(object.getId()).size();
		totalPublishedProgressLogs = this.repository.findTotalPublishedProgressLogsByContractId(object.getId()).size();
		super.state(numberProgressLogsPerContract>0, "*", "client.contract.form.error.at-least-one-progressLog");;
		super.state(totalPublishedProgressLogs != null && totalPublishedProgressLogs == numberProgressLogsPerContract, "*", "client.contract.form.error.not-published-progressLogs");

		Double totalBudgets;
		Project project = object.getProject();
		if (project != null) {
			Double projectCost = this.moneyService.computeMoneyExchange(project.getCost(), "EUR").getTarget().getAmount();
			totalBudgets = this.repository.computeTotalBudgetsByProject(project.getId());
			super.state(totalBudgets != null && totalBudgets <= projectCost, "*", "client.contract.form.error.bad-budget");
		}
	}

	@Override
	public void perform(final Contract object) {
		assert object != null;

		Money systemCurrencyBudget;
		Money budget;

		budget = object.getBudget();
		systemCurrencyBudget = this.moneyService.computeMoneyExchange(budget, "EUR").getTarget();
		object.setSystemCurrencyBudget(systemCurrencyBudget);

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;

		projects = this.repository.findAllPublishedProjects();
		choices = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "instantiationMoment", "provider", "customer", "goals", "budget", "draftMode", "systemCurrencyBudget");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}

}
