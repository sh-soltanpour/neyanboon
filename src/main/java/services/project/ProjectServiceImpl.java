package services.project;

import factory.ObjectFactory;
import client.MetaDataClient;
import dtos.ProjectDto;
import dtos.SkillDto;
import entitites.Project;
import entitites.Skill;
import entitites.User;
import exceptions.AccessDeniedException;
import exceptions.NotFoundException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectServiceImpl implements ProjectService {

    private MetaDataClient metaDataClient = ObjectFactory.getMetaDataClient();
    private HashMap<String, Project> projects;
    private List<SkillDto> skills;

    public ProjectServiceImpl() {
        initialFetch();
    }

    @Override
    public void initialFetch() {
        projects = new HashMap<>
                (
                        metaDataClient
                                .getProjects()
                                .stream()
                                .map(ProjectDto::toProject)
                                .collect(Collectors.toMap(Project::getId, project -> project))
                );
        skills = metaDataClient.getSkills();
    }

    @Override
    public List<ProjectDto> getProjects() {
        return Collections.emptyList();
    }

    @Override
    public ProjectDto getProject(String projectId) {
        return ProjectDto.of(projects.get(projectId));
    }

    @Override
    public ProjectDto getProject(User user, String projectId) throws NotFoundException, AccessDeniedException {
        Project project = projects.get(projectId);
        if (project == null)
            throw new NotFoundException();
        else if (!isQualified(user, project))
            throw new AccessDeniedException();
        return ProjectDto.of(project);
    }

    @Override
    public List<ProjectDto> getQualifiedProjects(User user) {
        return projects
                .values()
                .stream()
                .filter(project -> isQualified(user, project))
                .map(ProjectDto::of)
                .collect(Collectors.toList());
    }

    public boolean isQualified(User user, Project project) {
        for (Skill requiredSkill : project.getSkills()) {
            Skill userSkill = user
                    .getSkills()
                    .stream()
                    .filter(skill -> skill.getName().equals(requiredSkill.getName()))
                    .findFirst()
                    .orElse(null);

            if (userSkill == null || userSkill.getPoints() < requiredSkill.getPoints())
                return false;
        }
        return true;
    }
}
