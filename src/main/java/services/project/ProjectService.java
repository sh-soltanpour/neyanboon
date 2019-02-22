package services.project;

import dtos.ProjectDto;
import entitites.User;

import java.util.List;

public interface ProjectService {
    void initialFetch();
    List<ProjectDto> getProjects();
    ProjectDto getProject(String projectId);
    List<ProjectDto> getQualifiedProjects(User user);
}
