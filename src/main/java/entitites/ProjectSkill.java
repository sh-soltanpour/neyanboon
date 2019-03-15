package entitites;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ProjectSkill {
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer point = null;

    public ProjectSkill() {
    }

    public ProjectSkill(String name, Integer point) {
        this.name = name;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
