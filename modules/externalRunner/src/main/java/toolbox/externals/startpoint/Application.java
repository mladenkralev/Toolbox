package toolbox.externals.startpoint;

import toolbox.exception.StartUpException;
import toolbox.project.core.Project;

import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * Created by mladen on 01/12/2017.
 */
public class Application {
    private static List<Project> projects = new ArrayList<Project>();
    public static String PROJECT_NAME = "project.name";
    public static String PROJECT_URL = "project.url";

    public static String PROJECT_NEXT = "project.next";


    public static String DOT = ".";

    public static void main(String[] args) throws StartUpException {
        try {
            if (args.length != 1) {
                throw new StartUpException("Args need to be supplied");
            }

            for (String argument : args) {
                System.out.println("User passed " + argument);
            }

            Path propertiesPath = Paths.get(args[0]);
            FileReader propertiesReader = new FileReader(propertiesPath.toAbsolutePath().toString());

            //determine projects
            Properties properties = new Properties();
            properties.load(propertiesReader);

            int conterOfProjects = 0;
            for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements(); ) {
                String key = (String) e.nextElement();
                String value = properties.getProperty(key);


                String nameSingleProject = properties.getProperty(PROJECT_NAME + DOT + conterOfProjects);
                String urlSingleProject = properties.getProperty(PROJECT_URL + DOT + conterOfProjects);

                if ((nameSingleProject != null) && (urlSingleProject != null)) {
                    Project project = new Project(nameSingleProject, urlSingleProject, conterOfProjects);
                    projects.add(project);
                    conterOfProjects++;
                }
            }



            //evaluate projects
            conterOfProjects = 0;
            for (Project project : projects) {
                String nextProjectName = properties.getProperty(PROJECT_NEXT + DOT + conterOfProjects++);
                for(Project nextProject: projects) {
                    if(nextProject.getName().equals(nextProjectName)) {
                        project.setNextProject(nextProjectName);
                        break;
                    }
                }
            }

            for (Project project : projects) {
                System.out.println(project);
            }

        } catch (Exception e) {
            throw new StartUpException("General startup exception");
        }
    }
}
