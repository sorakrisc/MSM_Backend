package msm_backend.methods;

import org.jsoup.Jsoup;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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



}
