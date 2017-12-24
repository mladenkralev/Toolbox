package toolbox.project.core;

import toolbox.project.interfaces.ICommit;
import toolbox.project.interfaces.IJenkinsRunnable;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by mlkr on 01/12/2017.
 */
// TODO NP checking and validation
public class Project implements ICommit, IJenkinsRunnable {
    private String name;
    private String url;
    private int numberOfProject;
    private String nextProject;
    private Boolean autoIncrement;
    private List<File> filesToIncrement;
    private String regex;

    public Project(String name, String url, Boolean autoIncrement, List<File> filesToIncrement, String regex, int numberOfProject) {
        this.name = name;
        this.url = url;
        this.autoIncrement = autoIncrement;
        this.numberOfProject = numberOfProject;
        this.regex = regex;
    }

    public Project(String name, String url, Boolean autoIncrement, int numberOfProject) {
        this.name = name;
        this.url = url;
        this.autoIncrement = autoIncrement;
        this.numberOfProject = numberOfProject;
    }

    public void increaseVersion() {

    }

    public void commitCoreFunctionallity() {

    }

    public void runOnJenkins() {

    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", numberOfProject=" + numberOfProject +
                ", nextProject='" + nextProject + '\'' +
                ", autoIncrement=" + autoIncrement +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getNumberOfProject() {
        return numberOfProject;
    }

    public String getNextProject() {
        return nextProject;
    }

    public void setNextProject(String nextProject) {
        this.nextProject = nextProject;
    }
}
