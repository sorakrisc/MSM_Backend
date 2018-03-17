package msm_backend.controller;


import msm_backend.domain.Course;
import msm_backend.domain.Plan;
import msm_backend.domain.User;
import msm_backend.methods.SkyConnector;
import msm_backend.repo.CourseRepo;
import msm_backend.repo.PlanRepo;
import msm_backend.repo.UserRepo;
import org.hibernate.boot.jaxb.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseRepo rp;

    @Autowired
    private PlanRepo planrp;

//    @Autowired
//    private UserRepo userrp;

    @RequestMapping("/findall")
    @ResponseBody
    public List<Course> findall() {
        return rp.findAll();
    }

    @RequestMapping(value="/updatecourse/{id}", method=RequestMethod.POST)
    @ResponseBody
    void update(@PathVariable("id") String id){

        String openSectionUrl= "https://sky.muic.mahidol.ac.th/public/open_sections_by_course_tags?term_id="+id;
        SkyConnector skyConnector = new SkyConnector();
        boolean status = skyConnector.getOpenSectionCourses(openSectionUrl);
        if (status){
            System.out.println("SUCESS GETOPENSECTION");
        } else{
            System.out.println("FAILED GETOPENSECTION");
            throw new IOException();
        }
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(value=HttpStatus.NOT_MODIFIED, reason="fail to connect with open section")
    void handleIOException(IOException err){
    }
    class IOException extends RuntimeException{
        private static final long serialVersionUID = 1L;
    }

//    @RequestMapping("/test")
//    @ResponseBody
//    void test(){
//        //Cleanup db tables
//        userrp.deleteAllInBatch();
//        planrp.deleteAllInBatch();
//        System.out.println("HELLO");
//
//        User u = new User("james", "cos@g.com","password123");
//        System.out.println("hi");
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        Plan p1 = new Plan(timestamp.toString());
//        p1.setUser(u);
//        Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
//        Plan p2 = new Plan(timestamp2.toString());
//        p2.setUser(u);
//        System.out.println("yo");
//        u.getPlans().add(p1);
//        u.getPlans().add(p2);
//
//        userrp.save(u);
//        System.out.println("done");
//    }
    @RequestMapping("/test1")
    @ResponseBody
    void test1(){

        rp.deleteAllInBatch();
        SkyConnector sc = new SkyConnector();
        sc.getOpenSectionCourses2("https://sky.muic.mahidol.ac.th/public/open_sections_by_course_tags?term_id="+16,rp);
    }
    @RequestMapping("/clear")
    @ResponseBody
    void clear(){
        rp.deleteAllInBatch();;
    }

}