
package acme.features.any.published_progress_logs;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.entities.contract.ProgressLog;

@Service
public class AnyPublishedProgressLogListService extends AbstractService<Any, ProgressLog> {

	@Autowired
	AnyPublishedProgressLogRepository cpr;
	
	public static final String MASTER = "masterId";


	@Override
	public void authorise() {
		int masterId;
		Contract contract;
		masterId = super.getRequest().getData(MASTER, int.class);
		contract = this.cpr.findOneContractById(masterId);
		super.getResponse().setAuthorised(!contract.getDraftMode());
	}

	@Override
	public void load() {
		Collection<ProgressLog> objects;
		int masterId;

		masterId = super.getRequest().getData(MASTER, int.class);
		objects = this.cpr.findProgressLogsByContractId(masterId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final ProgressLog object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "recordId", "completeness");

		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<ProgressLog> objects) {
		assert objects != null;

		int masterId;

		masterId = super.getRequest().getData(MASTER, int.class);
		super.getResponse().addGlobal(MASTER, masterId);
	}

}
