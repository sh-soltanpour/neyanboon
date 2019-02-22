package services.project;

import Factory.ObjectFactory;
import client.MetaDataClient;
import dtos.ProjectDto;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectServiceImpl implements ProjectService {

    private MetaDataClient metaDataClient = ObjectFactory.getMetaDataClient();
    private HashMap<String, ProjectDto> projects;


    @Override
    public void fetchProjects() {
        List<ProjectDto> fetchedProjects = metaDataClient.getProjects();
        projects = new HashMap<>
                (
                        fetchedProjects
                                .stream()
                                .collect(
                                        Collectors.toMap(ProjectDto::getId, project -> project))
                );
    }

    @Override
    public List<ProjectDto> getProjects() {
        return Collections.emptyList();
    }

    @Override
    public ProjectDto getProject(String projectId) {
        return projects.get(projectId);
    }
}
