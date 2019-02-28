package services.user;

import dtos.UserDto;
import entitites.Skill;
import entitites.User;
import exceptions.NotFoundException;
import factory.ObjectFactory;
import services.skill.SkillService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private User currentUser;
    private List<User> users = new ArrayList<>();
    private SkillService skillService = ObjectFactory.getSkillService();

    public UserServiceImpl() {
        initialize();
    }

    @Override
    public void initialize() {
        List<Skill> skills = new ArrayList<>(Arrays.asList(
                new Skill("HTML", 5),
                new Skill("Javascript", 4),
                new Skill("C++", 2),
                new Skill("Java", 3)
        ));
        currentUser = new User(
                "1",
                "علی",
                "شریف زاده",
                "برنامەنویس وب",
                null,
                skills,
                "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه ولی پول نداشت"
        );
        users.add(currentUser);
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public User getUser(String userId) throws NotFoundException {
        return users
                .stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteSkill(String skillName, User currentUser) throws NotFoundException {
        currentUser.deleteSkill(skillName);
    }

    @Override
    public void addSkill(String skillName, User currentUser) throws NotFoundException {
        Skill newSkill = findSkill(skillName);

        currentUser.addSkill(newSkill);
    }

    private Skill findSkill(String skillName) throws NotFoundException {
        return skillService.getSkills()
                .stream()
                .filter(skillDto -> skillDto.getName().equals(skillName))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public UserDto getUserDto(String userId) throws NotFoundException {
        return UserDto.of(getUser(userId));
    }

    @Override
    public List<UserDto> getOtherUsers() {
        return users
                .stream()
                .filter(user -> !user.getId().equals(currentUser.getId()))
                .map(UserDto::of)
                .collect(Collectors.toList());
    }
}
