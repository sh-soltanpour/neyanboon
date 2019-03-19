package entitites;

import exceptions.AlreadyExistsException;

import java.util.ArrayList;
import java.util.List;

public class UserSkill {
    //TODO: remove point, change name to UserSkill
    private String name;
    private List<User> endorsedBy;


    public UserSkill(String name) {
        this.name = name;
        this.endorsedBy = new ArrayList<>();
    }

    void endorse(User endorser) throws AlreadyExistsException {
        if (endorsedBy.stream().anyMatch(user -> user.getId().equals(endorser.getId())))
            throw new AlreadyExistsException("You have already endorsed this skill");
        endorsedBy.add(endorser);
    }

    public boolean hasEndorsedBy(User endorser) {
        return endorsedBy.stream().anyMatch(user -> user.getId().equals(endorser.getId()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return endorsedBy.size();
    }
    


}
