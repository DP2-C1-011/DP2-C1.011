
package acme.features.client.participates_in;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.ParticipatesIn;
import acme.roles.Client;

@Service
public class ClientParticipatesInListService extends AbstractService<Client, ParticipatesIn> {

	@Autowired
	ClientParticipatesInRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<ParticipatesIn> objects;
		int clientId;
		clientId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findManyParticipatesInByClientId(clientId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final ParticipatesIn object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code");
		dataset.put("project", object.getProject().getCode());
		super.getResponse().addData(dataset);
	}

}
