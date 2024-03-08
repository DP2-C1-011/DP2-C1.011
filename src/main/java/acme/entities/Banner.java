
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	@Past
	private Date				instantiationMoment;

	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date				displayPeriodStart;

	@NotNull
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date				displayPeriodEnd;

	@URL
	@NotBlank
	private String				linkPicture;

	@Length(max = 75)
	@NotBlank
	private String				slogan;

	@URL
	@NotBlank
	private String				linkDoc;

}
