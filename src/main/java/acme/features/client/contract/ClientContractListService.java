
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.roles.Client;

@Service
public class ClientContractListService extends AbstractService<Client, Contract> {

	@Autowired
	ClientContractRepository ccr;


	@Override
	public void authorise() {
		//TODO  creo que aqui tengo que comprobar que la persona tiene rol de client
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		//Si la persona tiene rol de client se traeran todos los contratos asociados a su id
		Collection<Contract> objects;
		int clientId;
		clientId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.ccr.findContractsByClientId(clientId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "instantiationMoment");

		super.getResponse().addData(dataset);
	}

}
