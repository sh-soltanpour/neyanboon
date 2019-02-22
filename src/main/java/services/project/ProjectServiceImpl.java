package services.project;

import Factory.ObjectFactory;
import client.MetaDataClient;
import dtos.ProjectDto;
import dtos.SkillDto;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectServiceImpl implements ProjectService {

    private MetaDataClient metaDataClient = ObjectFactory.getMetaDataClient();
    private HashMap<String, ProjectDto> projects;
    private List<SkillDto> skills;


    @Override
    public void initialFetch() {
        projects = new HashMap<>
                (
                        metaDataClient
                                .getProjects()
                                .stream()
                                .collect(Collectors.toMap(ProjectDto::getId, project -> project))
                );
        skills = metaDataClient.getSkills();
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
