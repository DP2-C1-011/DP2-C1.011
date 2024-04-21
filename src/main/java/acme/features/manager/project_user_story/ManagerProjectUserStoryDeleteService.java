
package acme.features.manager.project_user_story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.ProjectUserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryDeleteService extends AbstractService<Manager, ProjectUserStory> {

	@Autowired
	private ManagerProjectUserStoryRepository deleteRepository;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		ProjectUserStory link = this.deleteRepository.findLinkById(id);

		Principal principal = super.getRequest().getPrincipal();
		int userAccountId = principal.getAccountId();

		boolean status = link != null && principal.hasRole(Manager.class) && link.getProject().getManager().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		ProjectUserStory link = this.deleteRepository.findLinkById(id);
		super.getBuffer().addData(link);
	}
	@Override
	public void bind(final ProjectUserStory object) {
		assert object != null;
		super.bind(object, "userStory");
	}
	@Override
	public void validate(final ProjectUserStory object) {
		assert object != null;
	}
	@Override
	public void perform(final ProjectUserStory object) {
		assert object != null;
		this.deleteRepository.delete(object);
	}
	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;
		Dataset dataset = super.unbind(object, "userStory");
		super.getResponse().addData(dataset);
	}
}
