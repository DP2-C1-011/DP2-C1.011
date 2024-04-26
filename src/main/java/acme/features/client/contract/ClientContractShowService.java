
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.MoneyService;
import acme.components.SystemCurrencyRepository;
import acme.entities.contract.Contract;
import acme.entities.project.Project;
import acme.roles.Client;

@Service
public class ClientContractShowService extends AbstractService<Client, Contract> {

	@Autowired
	ClientContractRepository	ccr;

	@Autowired
	SystemCurrencyRepository	systemRepository;

	@Autowired
	MoneyService				moneyService;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Contract contract;
		Client client;

		masterId = super.getRequest().getData("id", int.class);
		contract = this.ccr.findContractById(masterId);
		client = contract == null ? null : contract.getClient();
		status = super.getRequest().getPrincipal().hasRole(client) || contract != null && !contract.getDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Contract object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.ccr.findContractById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Contract object) {

		assert object != null;

		int clientId;
		Collection<Project> projects;
		SelectChoices choices;
		Dataset dataset;
		String systemCurrency = this.systemRepository.getSystemConfiguration().get(0).getSystemCurrency();
		Money systemCurrencyBudget;

		if (!object.getBudget().getCurrency().equals("EUR"))
			systemCurrencyBudget = this.moneyService.computeMoneyExchange(object.getBudget(), systemCurrency).getTarget();
		else
			systemCurrencyBudget = object.getBudget();

		if (!object.getDraftMode())
			projects = this.ccr.findAllProjects();
		else {
			clientId = super.getRequest().getPrincipal().getActiveRoleId();
			projects = this.ccr.findAllPublishedProjects();
		}
		choices = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "instantiationMoment", "provider", "customer", "goals", "budget", "draftMode");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("systemCurrencyBudget", systemCurrencyBudget);
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);

	}

}
