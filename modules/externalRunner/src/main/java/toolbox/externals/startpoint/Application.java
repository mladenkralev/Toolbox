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
    // TODO Move to util
    public static String PROJECT_NAME = "project.name";
    public static String PROJECT_URL = "project.url";
    public static String PROJECT_AUTO_INCREMENT = "project.auto.increment";
    public static String PROJECT_NEXT = "project.next";
    public static String PROJECT_UPDATE_FILES= "project.increase.value.files";
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

            //load properties from file
            Properties properties = new Properties();
            properties.load(propertiesReader);

            //determine projects
            determineteProjects(properties);

            //evaluate projects
            evaluateProjects(properties);

            iterateThroughProjects(projects.get(0), projects);

        } catch (Exception e) {
            throw new StartUpException("General startup exception\n" + e.getMessage());
        }
    }

    private static void iterateThroughProjects(Project project, List<Project> projects) {
        String nextProject = project.getNextProject();
        if (nextProject != null) {
            for (Project findNextProject : projects) {
                if (findNextProject.getName().equals(nextProject)) {
                    System.out.println(String.format("Project %s depends on %s ",
                            project.getName(),
                            findNextProject.getName()));

                    iterateThroughProjects(findNextProject, projects);
                }
            }
        } else {
            System.out.println(String.format("Project %s is last! ",
                    project.getName()));
        }
    }

    private static void determineteProjects(Properties properties) {
        int conterOfProjects = 0;
        for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements(); ) {
            String key = (String) e.nextElement();
            String value = properties.getProperty(key);

            String nameSingleProject = properties.getProperty(PROJECT_NAME + DOT + conterOfProjects);
            String urlSingleProject = properties.getProperty(PROJECT_URL + DOT + conterOfProjects);
            String autoIncrementSingleProject = properties.getProperty(PROJECT_AUTO_INCREMENT +DOT + conterOfProjects);
            // TODO more checking and robust code!
            if ((nameSingleProject != null) && (urlSingleProject != null && (autoIncrementSingleProject != null))) {
                if(autoIncrementSingleProject.equals("false")){
                    String filesSingleProject = properties.getProperty(PROJECT_UPDATE_FILES + DOT + conterOfProjects);
                    String pathToFiles = filesSingleProject.trim();
                    System.out.println(pathToFiles);

                    Project project = new Project(nameSingleProject, urlSingleProject, new Boolean(autoIncrementSingleProject), conterOfProjects);
                    projects.add(project);
                } else {
                    Project project = new Project(nameSingleProject, urlSingleProject, new Boolean(autoIncrementSingleProject), conterOfProjects);
                    projects.add(project);
                }

                conterOfProjects++;
            }
        }
    }

    private static void evaluateProjects(Properties properties) {
        int conterOfProjects;
        conterOfProjects = 0;
        for (Project project : projects) {
            String nextProjectName = properties.getProperty(PROJECT_NEXT + DOT + conterOfProjects++);
            for (Project nextProject : projects) {
                if (nextProject.getName().equals(nextProjectName)) {
                    project.setNextProject(nextProjectName);
                    break;
                }
            }
        }
    }
}
