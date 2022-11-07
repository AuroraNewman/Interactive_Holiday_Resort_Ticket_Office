import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Activity implements Comparable<Activity> {
    private int numberActivities;
    private int numberCustomers;
    private String activityName;
    private int ticketsAvailable;

    public Activity(String activityName, int ticketsAvailable) {
        this.activityName = activityName;
        this.ticketsAvailable = ticketsAvailable;
    }
    public String getActivityName(){
        return activityName;
    }
    public void setActivityName() {
        this.activityName=activityName;
    }
    public int getTicketsAvailable() {
        return ticketsAvailable;
    }
    public void setTicketsAvailable() {
        this.ticketsAvailable=ticketsAvailable;
    }
    public int getNumberActivities() {return numberActivities;}
    public void setNumberActivities() {this.numberActivities=numberActivities;}
    @Override
    public String toString() {
        return ticketsAvailable + " tickets available for " + activityName + ".";
    }
    @Override
    public int compareTo(Activity a) {
        int activityCompare = activityName.compareTo(a.activityName);
        return activityCompare;
    }
}
