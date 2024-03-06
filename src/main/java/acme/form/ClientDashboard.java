
package acme.form;

import acme.client.data.AbstractForm;
import acme.client.data.datatypes.Money;

public class ClientDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	private Integer				progressLogsCompletenessBelow25;

	private Integer				progressLogCompletenessBetween25And50;

	private Integer				progressLogCompletenessBetween50And75;

	private Integer				progressLogsCompletenessAbove75;

	private Money				averageContractBudget;

	private Money				deviationContractBudget;

	private Money				minContractBudget;

	private Money				maxContractBudget;

}
