package services.project;

import dtos.ProjectDto;
import dtos.SkillDto;
import entitites.Skill;
import entitites.User;
import exceptions.AccessDeniedException;
import exceptions.AlreadyExistsException;
import exceptions.NotFoundException;

import java.util.List;

public interface ProjectService {
    void initialFetch();
    List<ProjectDto> getProjects();
    ProjectDto getProject(String projectId);
    ProjectDto getProject(User user, String projectId) throws NotFoundException, AccessDeniedException;
    List<ProjectDto> getQualifiedProjects(User user);
    void addBidRequest(String projectId, User user, int amount) throws NotFoundException, AccessDeniedException, AlreadyExistsException;
    boolean bidRequested(String userId, String projectId);
}
