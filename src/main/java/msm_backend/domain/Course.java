package msm_backend.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int CourseId;
    private String CourseName;
    private String CourseType;
    private String CourseSection;
    private String CourseCapacity;
    private String CourseTime;
    private String CourseDuration;
    private String CourseRoom;
    private String CourseInstructor;
    private String CourseFinal;
    private String Remark;
    public int getCourseId(){
        return this.CourseId;
    }

    public void setCourseId(int courseId) {
        this.CourseId = courseId;
    }

    public String getCourseName() {
        return this.CourseName;
    }

    public void setCourseName(String courseName) {
        this.CourseName = courseName;
    }

    public Course(int id, String name){
        super();
        this.CourseId = id;
        this.CourseName = name;
    }
    public Course(){
        super();
    }

    public String getCourseType() {
        return CourseType;
    }

    public void setCourseType(String courseType) {
        CourseType = courseType;
    }

    public String getCourseSection() {
        return CourseSection;
    }

    public void setCourseSection(String courseSection) {
        CourseSection = courseSection;
    }

    public String getCourseCapacity() {
        return CourseCapacity;
    }

    public void setCourseCapacity(String courseCapacity) {
        CourseCapacity = courseCapacity;
    }

    public String getCourseTime() {
        return CourseTime;
    }

    public void setCourseTime(String courseTime) {
        CourseTime = courseTime;
    }

    public String getCourseDuration() {
        return CourseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        CourseDuration = courseDuration;
    }

    public String getCourseRoom() {
        return CourseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        CourseRoom = courseRoom;
    }

    public String getCourseInstructor() {
        return CourseInstructor;
    }

    public void setCourseInstructor(String courseInstructor) {
        CourseInstructor = courseInstructor;
    }

    public String getCourseFinal() {
        return CourseFinal;
    }

    public void setCourseFinal(String courseFinal) {
        CourseFinal = courseFinal;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
