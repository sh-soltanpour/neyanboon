package services.user;

import dtos.UserDto;
import entitites.ProjectSkill;
import entitites.User;
import entitites.UserSkill;
import exceptions.NotFoundException;
import factory.ObjectFactory;
import services.skill.SkillService;

import java.util.*;
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
        List<UserSkill> skills = new ArrayList<>(Arrays.asList(
                new UserSkill("HTML"),
                new UserSkill("Javascript"),
                new UserSkill("C++"),
                new UserSkill("Java")
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
        int idCounter = 2;
        for (int i = 0; i < 5; i++) {
            users.add(
                    new User(
                            String.valueOf(idCounter++),
                            "امیر",
                            "زاده",
                            "برنامە وب",
                            null,
                            skills.stream().map(userSkill -> new UserSkill(userSkill.getName()))
                                    .collect(Collectors.toList()),
                            "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه ولی پول نداشت"
                    )
            );
        }


        users.add(currentUser);
        users.forEach(user -> currentUser.getSkills().forEach(skill -> currentUser.endorse(user, skill.getName())));
//        users.add(user2);
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
    public void endorse(User endorser, User endorsed, String skillName) {

        endorsed.endorse(endorser, skillName);
    }

    @Override
    public Set<String> getEndorsedList(String endorser, String endorsed) {
        try {
            User endorserUser = getUser(endorser);
            User endorsedUser = getUser(endorsed);
            Set<String> result = new HashSet<>();
            for (UserSkill skill : endorsedUser.getSkills()) {
                if (skill.hasEndorsedBy(endorserUser))
                    result.add(skill.getName());
            }
            return result;

        } catch (NotFoundException e) {
            return new HashSet<>();
        }
    }

    @Override
    public void addSkill(String skillName, User currentUser) throws NotFoundException {
        ProjectSkill newSkill = findSkill(skillName);

        currentUser.addSkill(newSkill.getName());
    }

    private ProjectSkill findSkill(String skillName) throws NotFoundException {
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
