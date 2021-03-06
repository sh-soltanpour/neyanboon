package services.user;

import dtos.UserDto;
import entitites.Skill;
import entitites.User;
import entitites.UserSkill;
import exceptions.*;
import factory.ObjectFactory;
import org.apache.commons.codec.digest.DigestUtils;
import repositories.user.UserRepository;
import security.TokenService;
import services.skill.SkillService;

import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private SkillService skillService = ObjectFactory.getSkillService();
    private UserRepository usersRepository = ObjectFactory.getUserRepository();
    private TokenService tokenService = ObjectFactory.getTokenService();

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
        User currentUser = new User(
                "1",
                "علی",
                "شریف زاده",
                DigestUtils.sha256Hex("ali1"),
                "برنامەنویس وب",
                "https://storage.backtory.com/shahryar/oliver.jpg",
                skills,
                "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه ولی پول نداشت"
        );
        List<User> users = new ArrayList<>();
        int idCounter = 2;
        for (int i = 0; i < 5; i++) {
            users.add(
                    new User(
                            String.valueOf(idCounter++),
                            "امیر",
                            "زاده",
                            "amir1",
                            "برنامە وب",
                            "https://storage.backtory.com/shahryar/oliver.jpg",
                            skills.stream().map(userSkill -> new UserSkill(userSkill.getName()))
                                    .collect(Collectors.toList()),
                            "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه ولی پول نداشت"
                    )
            );
        }

        users.add(currentUser);
        users.forEach(user -> currentUser.getSkills().forEach(skill -> {
            try {
                if (user != currentUser)
                    currentUser.endorse(user, skill.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
//        users.forEach(user -> {
//            try {
//                usersRepository.save(user);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        });
    }

    @Override
    public void register(UserDto userDto) throws AlreadyExistsException {
        User user = userDto.toUser();
        user.setPassword(DigestUtils.sha256Hex(userDto.getPassword()));
        usersRepository.register(user);
    }

    @Override
    public String login(String id, String password) throws SQLException, ForbiddenException {
        try {
            User user = usersRepository.findById(id);
            if (!DigestUtils.sha256Hex(password).equals(user.getPassword())) {
                throw new ForbiddenException();
            }
            return tokenService.generateToken(user.getId());
        } catch (NotFoundException e) {
            throw new ForbiddenException();
        }
    }



    @Override
    public User getUser(String userId) throws NotFoundException, InternalErrorException {
        try {
            return usersRepository.findById(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InternalErrorException();
        }
    }

    @Override
    public void deleteSkill(String skillName, User currentUser) throws NotFoundException, SQLException {
        currentUser.deleteSkill(skillName);
        usersRepository.save(currentUser);
    }


    @Override
    public void endorse(UserDto endorsedDto, String skillName, User currentUser)
            throws NotFoundException, PreConditionFailedException, AlreadyExistsException, InternalErrorException, SQLException {
        User endorsed = getUser(endorsedDto.getId());
        endorsed.endorse(currentUser, skillName);
        usersRepository.save(endorsed);
    }

    @Override
    public Set<String> getEndorsedList(String endorser, String endorsed) throws InternalErrorException {
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
    public List<User> search(String query) throws SQLException {
        return usersRepository.findByNameContains(query);
    }

    @Override
    public void addSkill(String skillName, User currentUser)
            throws NotFoundException, AlreadyExistsException, SQLException {
        Skill newSkill = skillService.getSkill(skillName);
        currentUser.addSkill(newSkill.getName());
        usersRepository.save(currentUser);
    }


    @Override
    public UserDto getUserDto(String userId) throws NotFoundException, InternalErrorException {
        return UserDto.of(getUser(userId));
    }

    @Override
    public List<UserDto> getOtherUsers(User currentUser) {
        try {
            return usersRepository.findAll().stream()
                    .filter(user -> !user.getId().equals(currentUser.getId()))
                    .map(UserDto::of)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
