package entitites;

import java.util.ArrayList;
import java.util.List;

public class Skill {
    private String name;
    private int point;
    private List<User> endorsedBy;


    public Skill(String name) {
        this.name = name;
        this.point = point;
        this.endorsedBy = new ArrayList<>();
    }

    void endorse(User endorser) {
        if (endorsedBy.stream().anyMatch(user -> user.getId().equals(endorser.getId())))
            return;
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

    public void setPoints(int point) {
        this.point = point;
    }


}
