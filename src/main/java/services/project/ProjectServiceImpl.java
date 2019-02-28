package services.project;

import client.MetaDataClient;
import dtos.ProjectDto;
import dtos.SkillDto;
import entitites.Bid;
import entitites.Project;
import entitites.Skill;
import entitites.User;
import exceptions.AccessDeniedException;
import exceptions.AlreadyExistsException;
import exceptions.NotFoundException;
import factory.ObjectFactory;
import services.user.UserService;

import java.util.*;
import java.util.stream.Collectors;

public class ProjectServiceImpl implements ProjectService {

    private MetaDataClient metaDataClient = ObjectFactory.getMetaDataClient();
    private HashMap<String, Project> projects;
    private List<Bid> bids = new ArrayList<>();
    private UserService userService = ObjectFactory.getUserService();

    public ProjectServiceImpl() {
        initialFetch();
    }

    @Override
    public void initialFetch() {
        projects = new HashMap<>
                (
                        metaDataClient
                                .getProjects()
                                .stream()
                                .map(ProjectDto::toProject)
                                .collect(Collectors.toMap(Project::getId, project -> project))
                );
    }

    @Override
    public List<ProjectDto> getProjects() {
        return Collections.emptyList();
    }

    @Override
    public ProjectDto getProject(String projectId) {
        return ProjectDto.of(projects.get(projectId));
    }

    @Override
    public ProjectDto getProject(User user, String projectId) throws NotFoundException, AccessDeniedException {
        Project project = projects.get(projectId);
        if (project == null)
            throw new NotFoundException();
        else if (!isQualified(user, project))
            throw new AccessDeniedException();
        return ProjectDto.of(project);
    }

    @Override
    public List<ProjectDto> getQualifiedProjects(User user) {
        return projects
                .values()
                .stream()
                .filter(project -> isQualified(user, project))
                .map(ProjectDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public void addBidRequest(String projectId, User user, int amount)
            throws NotFoundException, AccessDeniedException, AlreadyExistsException {
        Project project = projects.get(projectId);
        if (project == null)
            throw new NotFoundException();
        if (!isQualified(user, project))
            throw new AccessDeniedException();
        if (findBid(user, project) != null)
            throw new AlreadyExistsException("bid already exists");
        Bid bid = new Bid(user, project, amount);
        bids.add(bid);
    }

    @Override
    public boolean bidRequested(String userId, String projectId) {
        User user;
        try {
            user = userService.getUser(userId);
        } catch (NotFoundException e) {
            user = null;
        }
        Project project = projects.get(projectId);
        if (project == null || user == null)
            return false;
        return findBid(user, project) != null;
    }


    private Bid findBid(User user, Project project) {
        return bids
                .stream()
                .filter
                        (bid ->
                                bid.getBiddingUser().getId().equals(user.getId()) &&
                                        bid.getProject().getId().equals(project.getId())
                        )
                .findFirst()
                .orElse(null);
    }

    private boolean isQualified(User user, Project project) {
        for (Skill requiredSkill : project.getSkills()) {
            Skill userSkill = user
                    .getSkills()
                    .stream()
                    .filter(skill -> skill.getName().equals(requiredSkill.getName()))
                    .findFirst()
                    .orElse(null);

            if (userSkill == null || userSkill.getPoints() < requiredSkill.getPoints())
                return false;
        }
        return true;
    }
}
