package msm_backend.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int PlanId;
    private String PlanDate;

    private String PlanNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "plan_course", joinColumns = @JoinColumn(name = "plan_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses;

    public int getPlanId() {
        return PlanId;
    }

    public void setPlanId(int planId) {
        PlanId = planId;
    }

    public String getPlanDate() {
        return PlanDate;
    }

    public void setPlanDate(String planDate) {
        PlanDate = planDate;
    }

    public Plan(String number,String date){
        super();
        this.PlanDate = date;
        this.PlanNumber = number;
    }
    public Plan(){
        super();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public String getPlanNumber() {
        return PlanNumber;
    }

    public void setPlanNumber(String planNumber) {
        PlanNumber = planNumber;
    }
}