
package acme.features.manager.dashboard;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.UsPriority;
import acme.entities.project.UserStory;
import acme.form.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {

	@Autowired
	private ManagerDashboardRepository repository;

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

		List<Object[]> averageProjectCost;

		List<Object[]> deviationProjectCost;

		List<Object[]> minProjectCost;

		List<Object[]> maxProjectCost;

		Collection<UserStory> us = this.repository.findAllUserStories(managerId);

		if (us.isEmpty()) {
			averageUsCost = 0.0;
			desviationUsCost = 0.0;
			minUsCost = 0.0;
			maxUsCost = 0.0;
			mustNumber = 0;

			shouldNumber = 0;

			couldNumber = 0;

			wontNumber = 0;

		} else {
			averageUsCost = this.repository.averageEstimationUserStories(managerId);
			desviationUsCost = this.repository.deviationEstimationUserStories(managerId);
			minUsCost = this.repository.minEstimationUserStories(managerId);
			maxUsCost = this.repository.maxEstimationUserStories(managerId);
			mustNumber = this.repository.countUSbyPriority(UsPriority.Must);

			shouldNumber = this.repository.countUSbyPriority(UsPriority.Should);

			couldNumber = this.repository.countUSbyPriority(UsPriority.Could);

			wontNumber = this.repository.countUSbyPriority(UsPriority.Wont);

		}

		managerDashboard = new ManagerDashboard();

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

		managerDashboard.setAverageProjectCost(averageProjectCost);

		managerDashboard.setDeviationProjectCost(deviationProjectCost);

		managerDashboard.setMaxProjectCost(maxProjectCost);

		managerDashboard.setMinProjectCost(minProjectCost);

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
