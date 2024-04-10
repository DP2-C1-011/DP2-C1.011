
package acme.features.manager.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.MoneyService;
import acme.components.SystemCurrencyRepository;
import acme.entities.project.UsPriority;
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

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Integer managerId = super.getRequest().getPrincipal().getActiveRoleId();
		ManagerDashboard managerDashboard;

		Integer mustNumber;

		Integer shouldNumber;

		Integer couldNumber;

		Integer wontNumber;

		Double averageUsCost;

		Double desviationUsCost;

		Double minUsCost;

		Double maxUsCost;

		Double averageProjectCost;

		Double deviationProjectCost;

		Double minProjectCost;

		Double maxProjectCost;

		mustNumber = this.repository.countUSbyPriority(UsPriority.Must);

		shouldNumber = this.repository.countUSbyPriority(UsPriority.Should);

		couldNumber = this.repository.countUSbyPriority(UsPriority.Could);

		wontNumber = this.repository.countUSbyPriority(UsPriority.Wont);

		averageUsCost = this.repository.averageEstimationUserStories(managerId);
		desviationUsCost = this.repository.deviationEstimationUserStories(managerId);
		minUsCost = this.repository.minEstimationUserStories(managerId);
		maxUsCost = this.repository.maxEstimationUserStories(managerId);

		managerDashboard = new ManagerDashboard();

		String systemCurrency = this.systemRepository.getSystemConfiguration().get(0).getSystemCurrency();

		averageProjectCost = this.repository.averageProjectCost(managerId);

		deviationProjectCost = this.repository.deviationProjectCost(managerId);

		minProjectCost = this.repository.minProjectCost(managerId);

		maxProjectCost = this.repository.maxProjectCost(managerId);

		managerDashboard.setMustNumber(mustNumber);
		managerDashboard.setShouldNumber(shouldNumber);
		managerDashboard.setCouldNumber(couldNumber);
		managerDashboard.setWontNumber(wontNumber);

		managerDashboard.setAverageUsCost(averageUsCost);
		managerDashboard.setDesviationUsCost(desviationUsCost);
		managerDashboard.setMinUsCost(minUsCost);
		managerDashboard.setMaxUsCost(maxUsCost);

		Money averageMoney = new Money();
		averageMoney.setAmount(averageProjectCost);
		averageMoney.setCurrency("EUR");
		managerDashboard.setAverageProjectCost(this.moneyService.computeMoneyExchange(averageMoney, systemCurrency).getTarget());

		Money maxMoney = new Money();
		maxMoney.setAmount(maxProjectCost);
		maxMoney.setCurrency("EUR");
		managerDashboard.setMaxProjectCost(this.moneyService.computeMoneyExchange(maxMoney, systemCurrency).getTarget());

		Money minMoney = new Money();
		minMoney.setAmount(minProjectCost);
		minMoney.setCurrency("EUR");
		managerDashboard.setMinProjectCost(this.moneyService.computeMoneyExchange(minMoney, systemCurrency).getTarget());

		Money deviationMoney = new Money();
		deviationMoney.setAmount(deviationProjectCost);
		deviationMoney.setCurrency("EUR");
		managerDashboard.setDeviationProjectCost(this.moneyService.computeMoneyExchange(deviationMoney, systemCurrency).getTarget());

		super.getBuffer().addData(managerDashboard);
	}

	@Override
	public void unbind(final ManagerDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"mustNumber", "shouldNumber", "couldNumber", "wontNumber", "averageUsCost", // 
			"desviationUsCost", "minUsCost", //
			"maxUsCost", "averageProjectCost", //
			"deviationProjectCost", "minProjectCost", "maxProjectCost");

		super.getResponse().addData(dataset);
	}

}
