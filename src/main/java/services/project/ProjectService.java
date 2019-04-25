package services.project;

import dtos.ProjectDto;
import entitites.User;
import exceptions.*;

import java.util.List;

public interface ProjectService {
    void initialFetch();

    List<ProjectDto> getProjects();

    ProjectDto getProject(String projectId);

    ProjectDto getProject(User user, String projectId) throws NotFoundException, AccessDeniedException;

    List<ProjectDto> getQualifiedProjects(User user);

    void addBidRequest(String projectId, User user, int amount)
            throws NotFoundException, AccessDeniedException, AlreadyExistsException, BadRequestException;

    boolean bidRequested(String userId, String projectId) throws InternalErrorException;
}
