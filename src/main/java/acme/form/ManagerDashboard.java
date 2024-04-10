
package acme.form;

import acme.client.data.AbstractForm;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	Integer						mustNumber;

	Integer						shouldNumber;

	Integer						couldNumber;

	Integer						wontNumber;

	Double						averageUsCost;

	Double						desviationUsCost;

	Double						minUsCost;

	Double						maxUsCost;

	Money						averageProjectCost;

	Money						deviationProjectCost;

	Money						minProjectCost;

	Money						maxProjectCost;

}
