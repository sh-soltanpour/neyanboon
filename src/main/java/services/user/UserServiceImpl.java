package services.user;

import dtos.UserDto;
import entitites.Skill;
import entitites.User;
import exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private User currentUser;
    private List<User> users = new ArrayList<>();

    public UserServiceImpl() {
        initialize();
    }

    @Override
    public void initialize() {
        List<Skill> skills = Arrays.asList(
                new Skill("HTML", 5),
                new Skill("Javascript", 4),
                new Skill("C++", 2),
                new Skill("Java", 3)
        );
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
