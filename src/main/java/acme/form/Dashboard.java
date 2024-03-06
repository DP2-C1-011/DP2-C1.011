
package acme.form;

import java.util.Map;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard extends AbstractForm {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	Map<String, Integer>		totalNumberPerTypeOfPriority;

	Double						averageUsCost;

	Double						desviationUsCost;

	Double						minUsCost;

	Double						maxUsCost;

	Double						averageProjectCost;

	Double						deviationProjectCost;

	Double						minProjectCost;

	Double						maxProjectCost;

}
