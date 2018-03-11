package msm_backend.methods;

import msm_backend.domain.Course;
import msm_backend.repo.CourseRepo;
import org.jsoup.Jsoup;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class SkyConnector {

    public boolean getOpenSectionCourses(String url){
        try{
            System.out.println("conecting to skyos");
            org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
            org.jsoup.select.Elements rows = doc.select("tr");
            PrintWriter out = new PrintWriter(new FileWriter("CurrentCourses.txt", false), true);
            for(org.jsoup.nodes.Element row :rows)
            {
                org.jsoup.select.Elements columns = row.select("td");
                for (org.jsoup.nodes.Element column:columns)
                {
                    out.write(column.text());
                    out.write("|");
                }
                out.write("\n");
            }
            out.close();
            return true;
        } catch (IOException e){
            System.out.println(e);
            return false;
        }
    }
    public boolean getOpenSectionCourses2(String url, CourseRepo courserp){
        try {
            System.out.println("connecting to skyos");
            org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
            org.jsoup.select.Elements rows = doc.select("tr");
            Set<String> skyIDs = new HashSet<>();
            for(org.jsoup.nodes.Element row :rows)
            {
                org.jsoup.select.Elements columns = row.select("td");

                if (columns.size()==10) {
                    String skyID = columns.get(0).text();
                    if(!skyIDs.contains(skyID)) {
                        Course course = new Course();
                        course.setCourseSkyID(skyID);
                        skyIDs.add(skyID);
                        course.setCourseName(columns.get(1).text());
                        course.setCourseType(columns.get(2).text());
                        course.setCourseSection(columns.get(3).text());
                        course.setCourseCapacity(columns.get(4).text());
                        course.setCourseTime(columns.get(5).text());
                        course.setCourseRoom(columns.get(6).text());
                        course.setCourseInstructor(columns.get(7).text());
                        course.setCourseFinal(columns.get(8).text());
                        course.setRemark(columns.get(9).text().trim());
                        courserp.save(course);
                    }
                }

            }
            return true;
        } catch (IOException e){
            System.out.println(e);
            return false;
        }

    }


}
