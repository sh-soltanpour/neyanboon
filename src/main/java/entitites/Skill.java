package entitites;

public class Skill {
    private String name;
    private int point;

    public Skill(String name, int point) {
        this.name = name;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return point;
    }

    public void setPoints(int point) {
        this.point = point;
    }
}
