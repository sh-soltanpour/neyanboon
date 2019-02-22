package services.project;

import dtos.ProjectDto;

import java.util.List;

public interface ProjectService {
    void initialFetch();
    List<ProjectDto> getProjects();
    ProjectDto getProject(String projectId);
}
