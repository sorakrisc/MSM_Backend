package msm_backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int planid;
    private String plandate;

    private String plannumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "plancourse", joinColumns = @JoinColumn(name = "planid"), inverseJoinColumns = @JoinColumn(name = "courseid"))
    private Set<Course> courses;



    public Plan(String number,String date){
        super();
        this.plandate = date;
        this.plannumber = number;
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

    public int getPlanid() {
        return planid;
    }

    public void setPlanid(int planid) {
        this.planid = planid;
    }

    public String getPlandate() {
        return plandate;
    }

    public void setPlandate(String plandate) {
        this.plandate = plandate;
    }

    public String getPlannumber() {
        return plannumber;
    }

    public void setPlannumber(String plannumber) {
        this.plannumber = plannumber;
    }
}