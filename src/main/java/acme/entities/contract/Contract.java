
package acme.entities.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.project.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Contract extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	@NotNull
	private String				code;

	@Past
	@NotNull
	private Date				instantiationMoment;

	@NotBlank
	@Length(max = 75)
	@NotNull
	private String				provider;

	@NotBlank
	@Length(max = 75)
	@NotNull
	private String				customer;

	@NotBlank
	@Length(max = 100)
	@NotNull
	private String				goals;

	@NotNull
	//Comprobar en Servicio que budget es menor o igual que el presupuesto del proyecto
	private Money				budget;

	private Boolean				draftMode;

	@ManyToOne
	@NotNull
	private Project				project;

}
