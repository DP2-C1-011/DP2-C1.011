
package acme.features.developer.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.form.DeveloperDashboard;
import acme.roles.Developer;

@Service
public class DeveloperDashboardShowService extends AbstractService<Developer, DeveloperDashboard> {

	@Autowired
	private DeveloperDashboardRepository repository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Developer.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Integer developerId;
		DeveloperDashboard developerDashboard;
		Integer numTrainingModulesUpdated;
		Integer numTrainingSessionsLink;
		Double avgTrainingModulesTime;
		Double devTrainingModulesTime;
		Integer minTrainingModulesTime;
		Integer maxTrainingModulesTime;

		developerId = super.getRequest().getPrincipal().getActiveRoleId();
		numTrainingModulesUpdated = this.repository.numberOfTrainingModulesUpdated(developerId);
		numTrainingSessionsLink = this.repository.numberOfTrainingSessionsWithLink(developerId);
		avgTrainingModulesTime = this.repository.avgTrainingModuleTime(developerId);
		devTrainingModulesTime = this.repository.stdTrainingModuleTime(developerId);
		minTrainingModulesTime = this.repository.minTrainingModuleTime(developerId);
		maxTrainingModulesTime = this.repository.maxTrainingModuleTime(developerId);

		developerDashboard = new DeveloperDashboard();

		developerDashboard.setNumTrainingModulesUpdated(numTrainingModulesUpdated);
		developerDashboard.setNumTrainingSessionsLink(numTrainingSessionsLink);
		developerDashboard.setAvgTrainingModulesTime(avgTrainingModulesTime);
		developerDashboard.setDevTrainingModulesTime(devTrainingModulesTime);
		developerDashboard.setMinTrainingModulesTime(minTrainingModulesTime);
		developerDashboard.setMaxTrainingModulesTime(maxTrainingModulesTime);

		super.getBuffer().addData(developerDashboard);
	}

	@Override
	public void unbind(final DeveloperDashboard object) {
		Dataset dataset;
		dataset = super.unbind(object, "numTrainingModulesUpdated", "numTrainingSessionsLink", "avgTrainingModulesTime", "devTrainingModulesTime", "minTrainingModulesTime", "maxTrainingModulesTime");
		super.getResponse().addData(dataset);
	}

}
