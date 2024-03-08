
package acme.form;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Integer				numberOfPrincipalRoles;
	private Double				noticesRatio;
	private Double				objectivesStatusRatio;

	private Double				maximumValueRisks;
	private Double				minimumValueRisks;
	private Double				averageValuesRisks;
	private Double				standardDeviationValueRisks;

	private Integer				maximumClaims;
	private Integer				minimumClaims;
	private Double				averageClaims;
	private Double				standardDeviationClaims;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
