
package acme.features.any.published_contracts;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;

@Service
public class AnyPublishedContractsListService extends AbstractService<Any, Contract> {

	@Autowired
	AnyPublishedContractsRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Contract> objects;

		objects = this.repository.findAllPublishedContracts();

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
