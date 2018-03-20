package msm_backend.controller;

import msm_backend.domain.Course;
import msm_backend.domain.Plan;
//import msm_backend.domain.PlanCourse;
import msm_backend.domain.User;
import msm_backend.repo.CourseRepo;
import msm_backend.repo.PlanRepo;
import msm_backend.repo.UserRepo;
import msm_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private PlanRepo planrp;

    @Autowired
    private UserRepo userrp;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseRepo courserp;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/whoami")
    public ResponseEntity whoami(Authentication auth){
        Map<String, String> ret = new HashMap<String, String>(){{
            put("user", (String) auth.getPrincipal());
        }};
        return ResponseEntity.ok(ret);
    }
    @PostMapping("/validname")
    public ResponseEntity validname(@RequestParam("username") String username){
        if(userrp.findOneByName(username)!=null){
            return ResponseEntity.ok("invalid");
        }
        else{
            return ResponseEntity.ok("valid");
        }
    }

    @PostMapping("/validemail")
    public ResponseEntity validemail(@RequestParam("email") String email){
        if(userrp.findByEmail(email)!=null){
            return ResponseEntity.ok("invalid");
        }
        else{
            return ResponseEntity.ok("valid");
        }
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestParam("username") String username,
                                    @RequestParam("email") String email,
                                    @RequestParam("password") String password ){
        if(userrp.findOneByName(username)!=null || userrp.findByEmail(email)!=null){
            return ResponseEntity.badRequest().body("username or email is taken");
        }
        User newUser = new User(username, email, bCryptPasswordEncoder.encode(password));
        userService.saveUser(newUser);
        if(userrp.findOneByName(username)!=null){
            return ResponseEntity.ok("registered");
        } else{
            return ResponseEntity.badRequest().body("not registered");
        }

    }
    @RequestMapping(value= "/clear")
    @ResponseBody
    void clear(){
        userrp.deleteAllInBatch();
    }

    @PostMapping("/addPlan")
    @ResponseBody
    void addPlan(@RequestParam("username") String username,
                 @RequestParam("courseskyid") String courseskyid,
                 @RequestParam("planname") String planname ){
        System.out.println(courseskyid);
        System.out.println("hello");
        Course thisCourse = courserp.findOneBySkyid(courseskyid);
        System.out.println(thisCourse.getSkyid());
        if(thisCourse ==null){
            throw new NotFoundException();
        }
        User thisUser= userrp.findOneByName(username);
        System.out.println(thisUser.getUserid());
        if(thisUser == null){
            throw new NotFoundException();
        } else{
            System.out.println("sawadee");
            List<Plan> planlst =planrp.findByUser(thisUser);
            Plan thisplan = null;
            for(Plan p: planlst){
                if(p.getPlanNumber().equals(planname)){
                    thisplan = p;
                }
            }
            System.out.println("yo");
            Set<Course> setCourse = new HashSet<Course>();
            if(thisplan==null){
                thisplan = new Plan();
                thisplan.setPlanNumber(planname);

            } else{
                setCourse = thisplan.getCourses();
            }
            thisplan.setPlanDate(new Timestamp(System.currentTimeMillis()).toString());
            setCourse.add(thisCourse);
            thisplan.setCourses(setCourse);
            thisplan.setUser(thisUser);
            planrp.save(thisplan);

        }

    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_MODIFIED, reason="fail to connect with open section")
    void handleNotFoundException(CourseController.IOException err){
    }
    class NotFoundException extends RuntimeException{
        private static final long serialVersionUID = 1L;
    }


}
