package msm_backend.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int courseid;
    @Column(unique=true)
    private String skyid;
    private String name;
    private String type;
    private String section;
    private String capacity;
    private String time;
    private String duration;
    private String room;
    private String instructor;
    private String finaltime;
    private String remark;
    private String termid;


    public Course(int id, String name){
        super();
        this.courseid = id;
        this.name = name;
    }
    public Course(){
        super();
    }


    public String getSkyid() {
        return skyid;
    }

    public void setSkyid(String skyid) {
        this.skyid = skyid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getFinaltime() {
        return finaltime;
    }

    public void setFinaltime(String finaltime) {
        this.finaltime = finaltime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getTermid() {
        return termid;
    }

    public void setTermid(String termid) {
        this.termid = termid;
    }
}
