
package acme.features.any.published_contracts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;

@Service
public class AnyPublishedContractsShowService extends AbstractService<Any, Contract> {

	@Autowired
	AnyPublishedContractsRepository repository;


	@Override
	public void authorise() {
		int id;
		Contract object;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findContractById(id);
		super.getResponse().setAuthorised(!object.getDraftMode());
	}

	@Override
	public void load() {
		Contract object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findContractById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code", "instantiationMoment", "provider", "customer", "goals", "budget", "draftMode");
		dataset.put("project", object.getProject().getCode());
		super.getResponse().addData(dataset);
	}

}
