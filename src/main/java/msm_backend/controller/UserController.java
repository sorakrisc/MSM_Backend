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
        String username = (String) auth.getPrincipal();
        User user = userrp.findOneByName(username);

        Map<String, String> ret = new HashMap<String, String>(){{
            put("user",  username);
            put("role",  user.getRoles().toString());
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

    @GetMapping("/getplan")
    @ResponseBody
    public List<Plan> getplan(Authentication auth){
        System.out.println(planrp.findByUser(userrp.findOneByName((String)auth.getPrincipal())));
        return planrp.findByUser(userrp.findOneByName((String)auth.getPrincipal()));
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
        generateNewPlan(newUser,"1");
        generateNewPlan(newUser,"2");
        generateNewPlan(newUser,"3");
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


    @PostMapping("/removeCourseInPlan")
    @ResponseBody
    void removeCourseToPlan(Authentication auth,
                            @RequestParam("courseskyid") String courseskyid,
                            @RequestParam("planname") String planname) throws NotFoundException{
        String username = (String) auth.getPrincipal();
        User thisUser = userrp.findOneByName(username);
        Course thisCourse = courserp.findOneBySkyid(courseskyid);
        Plan thisPlan = findPlan(thisUser, planname);
        thisPlan.getCourses().remove(thisCourse);
        thisPlan.setPlandate(new Timestamp(System.currentTimeMillis()).toString());
        planrp.save(thisPlan);

    }

    void addCourseToPlanHelper(String courseskyid, String planname, Authentication auth){
        Course thisCourse = courserp.findOneBySkyid(courseskyid);
        String username = (String) auth.getPrincipal();
        User thisUser= userrp.findOneByName(username);
        Plan thisplan = findPlan(thisUser, planname);
        Set<Course> setCourse = new HashSet<Course>();
        if(thisplan==null){
            thisplan = new Plan();
            thisplan.setPlannumber(planname);

        } else{
            setCourse = thisplan.getCourses();
        }
        thisplan.setPlandate(new Timestamp(System.currentTimeMillis()).toString());
        setCourse.add(thisCourse);
        thisplan.setCourses(setCourse);
        thisplan.setUser(thisUser);
        planrp.save(thisplan);


    }
    @PostMapping("/addCourseToPlan")
    @ResponseBody
    void addCourseToPlan(@RequestParam("courseskyid") String courseskyid,
                 @RequestParam("planname") String planname, Authentication auth ){
        addCourseToPlanHelper(courseskyid, planname, auth);

    }
    public Plan findPlan(User thisUser, String planname){
        List<Plan> thisPlan = planrp.findByUser(thisUser);
        for(Plan p: thisPlan){
            if(p.getPlannumber().equals(planname)){
                return p;
            }
        }
        return null;
    }

    void addCoursesToPlanHelper(List<Course> courses, Plan plan){
        plan.getCourses().addAll(courses);
        planrp.save(plan);

    }
    void removeAllCoursesFromPlanHelper(Plan plan){
        plan.getCourses().clear();
        planrp.save(plan);
    }
    void generateNewPlan(User user, String planname){
        Plan plan = new Plan();
        plan.setPlandate(new Timestamp(System.currentTimeMillis()).toString());
        plan.setPlannumber(planname);
        plan.setUser(user);
        planrp.save(plan);
    }

    void saveCoursesToPlanHelper(List<Course> courses, String planname, String username){
        User thisUser = userrp.findOneByName(username);
        Plan thisPlan = findPlan(thisUser, planname);
        if(thisPlan!=null){
            removeAllCoursesFromPlanHelper(thisPlan);
            addCoursesToPlanHelper(courses, thisPlan);
        }
        else {
            generateNewPlan(thisUser, planname);
        }
    }
    @PostMapping("/removeAllCoursesFromPlan")
    @ResponseBody
    void removeAllCoursesFromPlan(@RequestParam("planname") String planname, Authentication auth){
        String username =(String) auth.getPrincipal();
        User thisUser = userrp.findOneByName(username);
        Plan thisPlan = findPlan(thisUser, planname);
        removeAllCoursesFromPlanHelper(thisPlan);
    }

    @PostMapping("/saveCoursesToPlan")
    @ResponseBody
    void saveCoursesToPlan(@RequestBody() List<Course> courses,
                          @RequestParam("planname") String planname, Authentication auth ){
        String username = (String) auth.getPrincipal();
        saveCoursesToPlanHelper(courses, planname, username);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_MODIFIED, reason="fail to connect with open section")
    void handleNotFoundException(CourseController.IOException err){
    }
    class NotFoundException extends RuntimeException{
        private static final long serialVersionUID = 1L;
    }



    void donothing(){}
}
