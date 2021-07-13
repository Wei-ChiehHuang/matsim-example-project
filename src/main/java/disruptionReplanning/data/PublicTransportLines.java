package disruptionReplanning.data;

public class PublicTransportLines {

    String system;
    String route;
    int line;
    String description;
    boolean interrupted;

    public PublicTransportLines(String system, String route, int line, String description, int interrupted) {
        this.system = system;
        this.route = route;
        this.line = line;
        this.description = description;
        this.interrupted = (interrupted==1);
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isInterrupted() {
        return interrupted;
    }

    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }

}
