
package acme.features.developer.training_session;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.training.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionUpdateService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int sessionId;
		TrainingSession session;
		Developer developer;

		sessionId = super.getRequest().getData("id", int.class);
		session = this.repository.findTrainingSessionById(sessionId);
		developer = session == null ? null : session.getTrainingModule().getDeveloper();
		status = session != null && session.getDraftMode() && super.getRequest().getPrincipal().hasRole(developer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingSessionById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;
		super.bind(object, "code", "startMoment", "finishMoment", "location", "instructor", "contactEmail", "optionalLink");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingSession existing;

			existing = this.repository.findTrainingSessionByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "developer.training-session.form.error.duplicateCode");
		}

		if (!super.getBuffer().getErrors().hasErrors("startMoment")) {
			super.state(MomentHelper.isAfter(object.getStartMoment(), object.getTrainingModule().getCreationMoment()), "startMoment", "developer.training-session.form.error.startBeforeCreate");
			super.state(MomentHelper.isAfter(object.getStartMoment(), MomentHelper.deltaFromMoment(object.getTrainingModule().getCreationMoment(), 7, ChronoUnit.DAYS)), "startMoment", "developer.training-session.form.error.startTooSoon");
		}

		if (!super.getBuffer().getErrors().hasErrors("startMoment") && !super.getBuffer().getErrors().hasErrors("finishMoment")) {
			super.state(MomentHelper.isAfter(object.getFinishMoment(), object.getStartMoment()), "finishMoment", "developer.training-session.form.error.finishBeforeStart");
			super.state(MomentHelper.isAfter(object.getFinishMoment(), MomentHelper.deltaFromMoment(object.getStartMoment(), 7, ChronoUnit.DAYS)), "finishMoment", "developer.training-session.form.error.periodTooShort");
		}
	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "startMoment", "finishMoment", "location", "instructor", "contactEmail", "optionalLink", "draftMode");
		super.getResponse().addData(dataset);
	}
}
