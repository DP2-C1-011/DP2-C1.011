
package acme.features.any.published_contracts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.MoneyService;
import acme.components.SystemCurrencyRepository;
import acme.entities.contract.Contract;

@Service
public class AnyPublishedContractsShowService extends AbstractService<Any, Contract> {

	@Autowired
	AnyPublishedContractsRepository repository;
	
	@Autowired
	SystemCurrencyRepository	systemRepository;

	@Autowired
	MoneyService				moneyService;


	@Override
	public void authorise() {
		int id;
		Contract object;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findContractById(id);
		super.getResponse().setAuthorised(!object.getDraftMode());
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
	public void unbind(final Contract object) {
		assert object != null;
		
		String systemCurrency = this.systemRepository.getSystemConfiguration().get(0).getSystemCurrency();
		Money systemCurrencyBudget;
		
		if (!object.getBudget().getCurrency().equals("EUR"))
			systemCurrencyBudget = this.moneyService.computeMoneyExchange(object.getBudget(), systemCurrency).getTarget();
		else
			systemCurrencyBudget = object.getBudget();

		Dataset dataset;
		dataset = super.unbind(object, "code", "instantiationMoment", "provider", "customer", "goals", "budget", "draftMode");
		dataset.put("project", object.getProject().getCode());
		dataset.put("systemCurrencyBudget", systemCurrencyBudget);
		super.getResponse().addData(dataset);
	}

}
