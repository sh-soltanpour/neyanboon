package services.project;

import dtos.ProjectDto;
import entitites.Project;
import entitites.User;
import exceptions.*;

import java.sql.SQLException;
import java.util.List;

public interface ProjectService {
    void fetchProjects();

    List<ProjectDto> getProjects();

    ProjectDto getProject(String projectId) throws NotFoundException, SQLException;

    ProjectDto getProject(User user, String projectId) throws NotFoundException, AccessDeniedException, SQLException;

    List<ProjectDto> getQualifiedProjects(User user) throws SQLException;

    List<ProjectDto> getQualifiedProjectsPaginated(User user, int pageNumber, int pageSize) throws SQLException;

    void addBidRequest(String projectId, User user, int amount)
            throws NotFoundException, AccessDeniedException, AlreadyExistsException, BadRequestException, SQLException;

    boolean bidRequested(String userId, String projectId) throws InternalErrorException, NotFoundException, SQLException;

    List<Project> search(String query) throws SQLException;
}
