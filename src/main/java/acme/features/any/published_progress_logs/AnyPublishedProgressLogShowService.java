
package acme.features.any.published_progress_logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.entities.contract.ProgressLog;

@Service
public class AnyPublishedProgressLogShowService extends AbstractService<Any, ProgressLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyPublishedProgressLogRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		ProgressLog object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findProgressLogById(id);
		Contract contract;
		contract = object.getContract();
		super.getResponse().setAuthorised(!object.getDraftMode() && !contract.getDraftMode());
	}

	@Override
	public void load() {
		ProgressLog object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findProgressLogById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final ProgressLog object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "recordId", "completeness", "comment", "registrationMoment", "responsible", "draftMode");
		dataset.put("masterId", object.getContract().getId());

		super.getResponse().addData(dataset);
	}

}
