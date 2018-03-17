package msm_backend.controller;

import msm_backend.domain.Course;
import msm_backend.domain.Plan;
//import msm_backend.domain.PlanCourse;
import msm_backend.domain.User;
import msm_backend.repo.PlanRepo;
import msm_backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private PlanRepo planrp;

    @Autowired
    private UserRepo userrp;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/whoami")
    public ResponseEntity whoami(Authentication auth){
        Map<String, String> ret = new HashMap<String, String>(){{
            put("user", (String) auth.getPrincipal());
        }};
        return ResponseEntity.ok(ret);
    }

    @RequestMapping(value="/register/{username}/{email}/{password}", method=RequestMethod.POST)
    @ResponseBody
    void register(@PathVariable("username") String username,
                @PathVariable("email") String email,
                @PathVariable("password") String password ){
        User newUser = new User(username, email, bCryptPasswordEncoder.encode(password));
        userrp.save(newUser);
    }
    @RequestMapping(value= "/clear")
    @ResponseBody
    void clear(){
        userrp.deleteAllInBatch();
    }


    @RequestMapping(value="/addPlan/{username}/{courses}/", method=RequestMethod.POST)
    @ResponseBody
    void addPlan(@PathVariable("username") String username,
                  @PathVariable("courses") String courses){
        User thisUser= null;
        List<User> allUser = userrp.findAll();
        for(User u: allUser){
            if(u.getName().equals(username)){
                thisUser = u;
                break;
            }
        }
        if(thisUser == null){
            throw new NotFoundException();
        } else{
            Plan newplan = new Plan(new Timestamp(System.currentTimeMillis()).toString());
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
