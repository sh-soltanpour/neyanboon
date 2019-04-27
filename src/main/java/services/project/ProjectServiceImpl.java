package services.project;

import client.MetaDataClient;
import dtos.ProjectDto;
import entitites.*;
import exceptions.*;
import factory.ObjectFactory;
import repositories.project.ProjectRepository;
import services.user.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectServiceImpl implements ProjectService {

    private MetaDataClient metaDataClient = ObjectFactory.getMetaDataClient();
    private List<Bid> bids = new ArrayList<>();
    private UserService userService = ObjectFactory.getUserService();
    private ProjectRepository projectRepository = ObjectFactory.getProjectRepository();

    public ProjectServiceImpl() {
        initialFetch();
    }

    @Override
    public void initialFetch() {
        List<Project> projects =
                metaDataClient
                        .getProjects()
                        .stream()
                        .map(ProjectDto::toProject)
                        .collect(Collectors.toList());
        int i = 0;
        for (Project project : projects) {
            if (i++ == 1)
                return;
            try {
                projectRepository.save(project);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<ProjectDto> getProjects() {
        return Collections.emptyList();
    }

    @Override
    public ProjectDto getProject(String projectId) throws NotFoundException, SQLException {
        return ProjectDto.of(projectRepository.findById(projectId));
    }

    @Override
    public ProjectDto getProject(User user, String projectId) throws NotFoundException, AccessDeniedException, SQLException {
        Project project = projectRepository.findById(projectId);
        if (project == null)
            throw new NotFoundException();
        else if (!isQualified(user, project))
            throw new AccessDeniedException();
        return ProjectDto.of(project);
    }

    @Override
    public List<ProjectDto> getQualifiedProjects(User user) throws SQLException {
        return projectRepository
                .findAll()
                .stream()
                .filter(project -> isQualified(user, project))
                .map(ProjectDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public void addBidRequest(String projectId, User user, int amount)
            throws NotFoundException, AccessDeniedException, AlreadyExistsException, BadRequestException, SQLException {
        Project project = projectRepository.findById(projectId);
        if (project == null)
            throw new NotFoundException();
        if (amount > project.getBudget())
            throw new BadRequestException("Entered amount is not valid");
        if (!isQualified(user, project))
            throw new AccessDeniedException();
        if (findBid(user, project) != null)
            throw new AlreadyExistsException("bid already exists");
        Bid bid = new Bid(user, project, amount);
        bids.add(bid);
    }

    @Override
    public boolean bidRequested(String userId, String projectId) throws InternalErrorException, NotFoundException, SQLException {
        User user;
        try {
            user = userService.getUser(userId);
        } catch (NotFoundException e) {
            user = null;
        }
        Project project = projectRepository.findById(projectId);
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
        for (ProjectSkill requiredSkill : project.getSkills()) {
            UserSkill userSkill = user
                    .getSkills()
                    .stream()
                    .filter(skill -> skill.getName().equals(requiredSkill.getName()))
                    .findFirst()
                    .orElse(null);

            if (userSkill == null || userSkill.getPoints() < requiredSkill.getPoint())
                return false;
        }
        return true;
    }
}
