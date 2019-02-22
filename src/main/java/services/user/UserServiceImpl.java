package services.user;

import entitites.Skill;
import entitites.User;

import java.util.Arrays;
import java.util.List;

public class UserServiceImpl implements UserService {
    private User currentUser;

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
                "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلیکارا بکنه ولی پول نداشت"
        );
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }
}
