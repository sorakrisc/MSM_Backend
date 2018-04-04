package msm_backend.controller;


import msm_backend.domain.*;
import msm_backend.methods.SkyConnector;
import msm_backend.repo.ConfigRepo;
import msm_backend.repo.CourseRepo;
import msm_backend.repo.PlanRepo;
import msm_backend.repo.UserRepo;
import org.hibernate.boot.jaxb.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseRepo rp;

    @Autowired
    private PlanRepo planrp;

    @Autowired
    private ConfigRepo configrp;

    @Autowired
    private UserRepo userrp;
    @RequestMapping("/findall")
    @ResponseBody
    public List<Course> findall() {
        System.out.println(rp.findAll());
        return rp.findAll();
    }

    @PostMapping("/findCourseWithTermId/")
    public List<Course> getcourseid(@RequestParam("termid") String termid){
        return rp.findByTermid(termid);
    }

    @RequestMapping(value="/updatecourse/{id}", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity update(@PathVariable("id") String id,Authentication auth){
        String username = (String) auth.getPrincipal();
        User user = userrp.findOneByName(username);
        Set<Role> userRoles = user.getRoles();
        if(userRoles.contains("admin")) {
            Config config = configrp.findOneByKey("recentterm");
            config.setValue(id);
            String openSectionUrl = "https://sky.muic.mahidol.ac.th/public/open_sections_by_course_tags?term_id=" + id;
            SkyConnector skyConnector = new SkyConnector();
            boolean status = skyConnector.getOpenSectionCourses(openSectionUrl, rp, id);
            if (status) {
                System.out.println("SUCESS GETOPENSECTION");
                return ResponseEntity.ok("success");
            } else {
                System.out.println("FAILED GETOPENSECTION");
                throw new IOException();
            }
        }
        else {
            return ResponseEntity.badRequest().body("admin access only");
        }
    }

    @PostMapping("/addCourseToPlan")
    public ResponseEntity addCourseToPlan(){
        Map<String, String> ret = new HashMap<>();
        return ResponseEntity.ok(ret);
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(value=HttpStatus.NOT_MODIFIED, reason="fail to connect with open section")
    void handleIOException(IOException err){
    }
    class IOException extends RuntimeException{
        private static final long serialVersionUID = 1L;
    }


    @RequestMapping("/clear")
    @ResponseBody
    void clear(){
        rp.deleteAllInBatch();;
    }

}