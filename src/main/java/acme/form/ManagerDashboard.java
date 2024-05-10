
package acme.form;

import java.util.List;

import acme.client.data.AbstractForm;
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

	List<Object[]>				averageProjectCost;
	List<Object[]>				deviationProjectCost;
	List<Object[]>				minProjectCost;
	List<Object[]>				maxProjectCost;

}
