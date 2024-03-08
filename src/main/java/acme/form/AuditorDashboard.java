
package acme.form;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Integer				totalNumberOfCodeAudits;
	private Double				averageNumberOfAuditRecordsAudit;
	private Double				deviationNumberOfAuditRecordsAudit;
	private Integer				minimunNumberOfAuditRecordsAudit;
	private Integer				maximunNumberOfAuditRecordsAudit;
	private Double				averageTimeOfPeriodLengthsAuditRecords;
	private Double				deviationTimeOfPeriodLengthsAuditRecords;
	private Double				minimumTimeOfPeriodLengthsAuditRecords;
	private Double				maximumTimeOfPeriodLengthsAuditRecords;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
