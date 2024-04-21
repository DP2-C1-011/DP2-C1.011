
package acme.features.manager.project_user_story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.ProjectUserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryShowService extends AbstractService<Manager, ProjectUserStory> {

	@Autowired
	private ManagerProjectUserStoryRepository showRepository;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		ProjectUserStory link = this.showRepository.findLinkById(id);

		Principal principal = super.getRequest().getPrincipal();
		int userAccountId = principal.getAccountId();

		boolean status = link != null && principal.hasRole(Manager.class) && link.getProject().getManager().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		ProjectUserStory link = this.showRepository.findLinkById(id);
		super.getBuffer().addData(link);
	}
	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;
		Dataset dataset = super.unbind(object, "userStory");
		super.getResponse().addData(dataset);
	}
}
