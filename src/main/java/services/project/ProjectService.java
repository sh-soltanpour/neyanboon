package services.project;

import dtos.ProjectDto;
import entitites.User;
import exceptions.AccessDeniedException;
import exceptions.NotFoundException;

import java.util.List;

public interface ProjectService {
    void initialFetch();
    List<ProjectDto> getProjects();
    ProjectDto getProject(String projectId);
    ProjectDto getProject(User user, String projectId) throws NotFoundException, AccessDeniedException;
    List<ProjectDto> getQualifiedProjects(User user);
}
