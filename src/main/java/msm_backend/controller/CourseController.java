package msm_backend.controller;


import msm_backend.domain.Course;
import msm_backend.methods.SkyConnector;
import msm_backend.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseRepo rp;

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
}