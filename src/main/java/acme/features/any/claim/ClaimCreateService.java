
package acme.features.any.claim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.Claim;

@Service
public class ClaimCreateService extends AbstractService<Any, Claim> {

	@Autowired
	private ClaimRepository createRepository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}
	@Override
	public void load() {
		Claim c = new Claim();
		c.setPublishIndication(true);
		super.getBuffer().addData(c);
	}
	@Override
	public void bind(final Claim c) {
		assert c != null;
		super.bind(c, "code", "instantiationMoment", "heading", "description", "department", "email", "link");
	}
	@Override
	public void validate(final Claim object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("publishIndication"))
			super.state(object.isPublishIndication(), "publishIndication", "any.claim.form.error.indication");
	}
	@Override
	public void perform(final Claim object) {
		assert object != null;
		object.setPublishIndication(false);
		this.createRepository.save(object);
	}
	@Override
	public void unbind(final Claim object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "instantiationMoment", "heading", "description", "department", "email", "link", "publishIndication");
		super.getResponse().addData(dataset);
	}
}
