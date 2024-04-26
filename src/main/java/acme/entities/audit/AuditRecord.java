
package acme.entities.audit;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import com.beust.jcommander.internal.Nullable;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AuditRecord extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------
	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Column(unique = true)
	@NotBlank
	@NotNull
	@Pattern(regexp = "AU-[0-9]{4}-[0-9]{3}", message = "{auditRecord.code.error}")
	private String				code;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	private Date				periodStart;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	private Date				periodEnd;

	@NotNull
	private Mark				mark;

	@URL
	@Nullable
	@Length(max = 255)
	private String				optionalLink;

	@NotNull
	private Boolean				draftMode			= true;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Long getPeriodInMinutes() {
		long diff = this.periodEnd.getTime() - this.periodStart.getTime();
		return Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(diff));
	}


	// Relationships ----------------------------------------------------------
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	private CodeAudit codeAudit;
}
