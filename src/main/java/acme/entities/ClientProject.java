
package acme.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import acme.client.data.AbstractEntity;
import acme.entities.project.Project;
import acme.roles.Client;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ClientProject extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@ManyToOne
	private Client				client;

	@NotNull
	@ManyToOne
	private Project				project;

}
