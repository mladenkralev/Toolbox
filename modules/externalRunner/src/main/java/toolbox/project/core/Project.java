package toolbox.project.core;

import toolbox.project.interfaces.ICommit;
import toolbox.project.interfaces.IJenkinsRunnable;

/**
 * Created by mlkr on 01/12/2017.
 */
public class Project implements ICommit, IJenkinsRunnable {
    private String name;
    private String url;
    private int numberOfProject;
    private String nextProject;

    public Project(String name, String url, int numberOfProject) {
        this.name = name;
        this.url = url;
        this.nextProject = nextProject;
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
                ", nextProject=" + nextProject +
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
