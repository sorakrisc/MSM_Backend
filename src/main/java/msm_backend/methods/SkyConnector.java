package msm_backend.methods;

import msm_backend.domain.Course;
import msm_backend.repo.CourseRepo;
import org.jsoup.Jsoup;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SkyConnector {

    public boolean getOpenSectionCourses(String url, CourseRepo courserp, String termid){
        try {
            org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
            org.jsoup.select.Elements rows = doc.select("tr");
            List<Course>currentCourses =  courserp.findAll();
            Set<String> currentSkyIDs =new HashSet<>();
            for (Course c : currentCourses){
                currentSkyIDs.add(c.getSkyid());
            }

            for(org.jsoup.nodes.Element row :rows)
            {
                org.jsoup.select.Elements columns = row.select("td");

                if (columns.size()==10) {
                    String skyID = columns.get(0).text();
                    if(!currentSkyIDs.contains(skyID)) {
                        Course course = new Course();
                        course.setSkyid(skyID);
                        currentSkyIDs.add(skyID);
                        course.setName(columns.get(1).text());
                        course.setType(columns.get(2).text());
                        course.setSection(columns.get(3).text());
                        course.setCapacity(columns.get(4).text());
                        course.setTime(columns.get(5).text());
                        course.setRoom(columns.get(6).text());
                        course.setInstructor(columns.get(7).text());
                        course.setFinaltime(columns.get(8).text());
                        course.setRemark(columns.get(9).text().trim());
                        course.setTermid(termid);
                        courserp.save(course);
                    }
                }

            }
            return true;
        } catch (IOException e){
            return false;
        }

    }


}
