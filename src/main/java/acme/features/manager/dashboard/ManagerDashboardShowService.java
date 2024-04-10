
package acme.features.manager.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.MoneyService;
import acme.components.SystemCurrencyRepository;
import acme.entities.contract.Contract;
import acme.form.ClientDashboard;
import acme.form.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {

	@Autowired
	private ManagerDashboardRepository	repository;

	@Autowired
	private SystemCurrencyRepository	systemRepository;

	@Autowired
	private MoneyService				moneyService;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}
	
	/*
	@Override
	public void load() {
		Integer clientId = super.getRequest().getPrincipal().getActiveRoleId();
		ClientDashboard clientDashboard;
		Double averageContractBudget;
		Double maxContractBudget;
		Double minContractBudget;
		Double deviationContractBudget;
		Integer progressLogsCompletenessBelow25;
		Integer progressLogsCompletenessBetween25And50;
		Integer progressLogsCompletenessBetween50And75;
		Integer progressLogsCompletenessAbove75;

		//averageContractBudget = this.repository.averageBudgetOfContractsPerClient(clientId);
		//maxContractBudget = this.repository.maximumBudgetOfContractsPerClient(clientId);
		//minContractBudget = this.repository.minimumBudgetOfContractsPerClient(clientId);
		//deviationContractBudget = this.repository.deviationBudgetOfContractsPerClient(averageContractBudget, clientId);
		progressLogsCompletenessBelow25 = this.repository.totalNumberOfProgressLogsCompletenessBelow25(clientId);
		progressLogsCompletenessBetween25And50 = this.repository.totalNumberOfProgressLogsCompletenessBetween25And50(clientId);
		progressLogsCompletenessBetween50And75 = this.repository.totalNumberOfProgressLogsCompletenessBetween50And75(clientId);
		progressLogsCompletenessAbove75 = this.repository.totalNumberOfProgressLogsCompletenessAbove75(clientId);

		clientDashboard = new ClientDashboard();

		String systemCurrency = this.systemRepository.getSystemConfiguration().get(0).getSystemCurrency();

		Collection<Contract> contracts = this.repository.findManyContractsByClientId(clientId);
		averageContractBudget = contracts.stream().map(c -> c.getSystemCurrencyBudget().getAmount()).mapToDouble(Double::doubleValue).average().getAsDouble();
		double total = 0.;
		for (Contract c : contracts)
			total += (c.getSystemCurrencyBudget().getAmount() - averageContractBudget) * (c.getSystemCurrencyBudget().getAmount() - averageContractBudget);
		deviationContractBudget = Math.sqrt(total / contracts.size());

		maxContractBudget = contracts.stream().map(c -> c.getSystemCurrencyBudget().getAmount()).max(Double::compare).get();
		minContractBudget = contracts.stream().map(c -> c.getSystemCurrencyBudget().getAmount()).min(Double::compare).get();

		Money averageMoney = new Money();
		averageMoney.setAmount(averageContractBudget);
		averageMoney.setCurrency("EUR");
		clientDashboard.setAverageContractBudget(this.moneyService.computeMoneyExchange(averageMoney, systemCurrency).getTarget());

		Money maxMoney = new Money();
		maxMoney.setAmount(maxContractBudget);
		maxMoney.setCurrency("EUR");
		clientDashboard.setMaxContractBudget(this.moneyService.computeMoneyExchange(maxMoney, systemCurrency).getTarget());

		Money minMoney = new Money();
		minMoney.setAmount(minContractBudget);
		minMoney.setCurrency("EUR");
		clientDashboard.setMinContractBudget(this.moneyService.computeMoneyExchange(minMoney, systemCurrency).getTarget());

		Money deviationMoney = new Money();
		deviationMoney.setAmount(deviationContractBudget);
		deviationMoney.setCurrency("EUR");
		clientDashboard.setDeviationContractBudget(this.moneyService.computeMoneyExchange(deviationMoney, systemCurrency).getTarget());

		clientDashboard.setProgressLogsCompletenessBelow25(progressLogsCompletenessBelow25);
		clientDashboard.setProgressLogsCompletenessBetween25And50(progressLogsCompletenessBetween25And50);
		clientDashboard.setProgressLogsCompletenessBetween50And75(progressLogsCompletenessBetween50And75);
		clientDashboard.setProgressLogsCompletenessAbove75(progressLogsCompletenessAbove75);

		super.getBuffer().addData(clientDashboard);
	}*/

	@Override
	public void unbind(final ManagerDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalNumberPerTypeOfPriority", "averageUsCost", // 
			"desviationUsCost", "minUsCost", //
			"maxUsCost", "averageProjectCost", //
			"deviationProjectCost", "minProjectCost", "maxProjectCost");

		super.getResponse().addData(dataset);
	}

}
