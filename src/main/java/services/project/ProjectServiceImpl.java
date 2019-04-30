package services.project;

import client.MetaDataClient;
import dtos.ProjectDto;
import entitites.*;
import exceptions.*;
import factory.ObjectFactory;
import repositories.bid.BidRepository;
import repositories.project.ProjectRepository;
import services.user.UserService;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ProjectServiceImpl implements ProjectService {

    private MetaDataClient metaDataClient = ObjectFactory.getMetaDataClient();
    private UserService userService = ObjectFactory.getUserService();
    private ProjectRepository projectRepository = ObjectFactory.getProjectRepository();
    private BidRepository bidRepository = ObjectFactory.getBidRepository();

    public ProjectServiceImpl() {
        fetchProjects();
    }

    @Override
    public void fetchProjects() {
        System.out.println("Fetching projects started");
        List<Project> projects =
                metaDataClient
                        .getProjects()
                        .stream()
                        .map(ProjectDto::toProject)
                        .collect(Collectors.toList());
        for (Project project : projects) {
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
        if (findBid(user, project))
            throw new AlreadyExistsException("bid already exists");
        Bid bid = new Bid(user, project, amount);
        bidRepository.save(bid);
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
        return findBid(user, project);
    }


    private Boolean findBid(User user, Project project) throws SQLException {
        return bidRepository.bidExists(project.getId(), user.getId());
    }

    private boolean isQualified(User user, Project project) {
        if (true)
            return true;
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
