
package acme.form;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	private Integer				numTrainingModulesUpdated;
	private Integer				numTrainingSessionsLink;
	private Double				avgTrainingModulesTime;
	private Double				devTrainingModulesTime;
	private Integer				minTrainingModulesTime;
	private Integer				maxTrainingModulesTime;

}
