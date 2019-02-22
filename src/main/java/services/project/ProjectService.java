package services.project;

import dtos.ProjectDto;

import java.util.List;

public interface ProjectService {
    void fetchProjects();
    List<ProjectDto> getProjects();
    ProjectDto getProject(String projectId);
}
