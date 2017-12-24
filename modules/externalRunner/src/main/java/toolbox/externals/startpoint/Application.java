package toolbox.externals.startpoint;

import toolbox.exception.StartUpException;
import toolbox.project.core.Project;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import static toolbox.utill.PropertyKeyConstnats.*;

/**
 * Created by mladen on 01/12/2017.
 */
public class Application {
    private static List<Project> projects = new ArrayList<Project>();

    public static void main(String[] args) throws StartUpException, IOException {
//        try {
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

//        } catch (Exception e) {
//            throw new StartUpException("General startup exception\n" + e.getMessage());
//        }
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

    private static void determineteProjects(Properties properties) throws StartUpException {
        int numberOfProject = 0;

        String externalsRoot= properties.getProperty("root.path");
        System.out.println(externalsRoot);
        if(externalsRoot == null) {
            throw new StartUpException("No root path found in properties file");
        }

        Path rootPath = Paths.get(externalsRoot).normalize();

        for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements(); ) {
            String key = (String) e.nextElement();
            String value = properties.getProperty(key);

            String projectNmae = properties.getProperty(PROJECT_NAME + DOT + numberOfProject);
            String urlProject = properties.getProperty(PROJECT_URL + DOT + numberOfProject);
            String isAutoIncrementAsString = properties.getProperty(PROJECT_AUTO_INCREMENT +DOT + numberOfProject);
            String regexFromProperties = properties.getProperty(PROJECT_REGEX + DOT + numberOfProject);

            Project project;

            // TODO more checking and robust code!
            if ((projectNmae != null) && (urlProject != null) && (isAutoIncrementAsString != null)
                    && (regexFromProperties != null)) {
                if (isAutoIncrementAsString.equals("false")) {
                    String filesToUpdateInSingleProject = properties.getProperty(PROJECT_UPDATE_FILES + DOT + numberOfProject);
                    String[] filesToUpdate = filesToUpdateInSingleProject.trim().split("\\,");

                    //replace since we ne nee to find the original intention(regex) of the person from properties
                    regexFromProperties = regexFromProperties.replaceAll(",", "\n");

                    System.out.println("\n\nRegex:\n" + regexFromProperties);

                    //get real and existing files to update
                    List<File> existingFilesToUpdate = collectFilesToUpdate(rootPath, projectNmae, filesToUpdate);

                    project = new Project(projectNmae, urlProject, new Boolean(isAutoIncrementAsString),
                            existingFilesToUpdate, regexFromProperties, numberOfProject);
                    projects.add(project);
                } else {
                    project = new Project(projectNmae, urlProject, new Boolean(isAutoIncrementAsString),
                            numberOfProject);

                    projects.add(project);
                }
            }
            numberOfProject++;
        }
    }

    private static List<File> collectFilesToUpdate(Path rootPath, String projectNmae, String[] filesToUpdate)
            throws StartUpException {

        List existingFilesToUpdate = new ArrayList();

        System.out.println(String.format("Project %s files to update:", projectNmae));
        for(String pathToFile: filesToUpdate){
            System.out.println(rootPath.normalize().toAbsolutePath().toString() +
                    File.separator+ pathToFile);

            // root + path to file
            File fileToUpdate = new File(rootPath.toAbsolutePath()
                    .normalize().toString()+ File.separator + pathToFile);
            if(!fileToUpdate.exists()) {
                throw new StartUpException(String.format("File to update %s "
                        + "does not exist.",fileToUpdate.getAbsolutePath().toString()));
            }
            existingFilesToUpdate.add(filesToUpdate);
        }

        if(existingFilesToUpdate.isEmpty()) {
            throw new StartUpException("List with update files is empty");
        }
        return existingFilesToUpdate;
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
