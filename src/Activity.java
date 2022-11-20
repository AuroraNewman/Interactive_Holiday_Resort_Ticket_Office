import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * The activity class stores information about the Activity object, namely the activity name and number of available tickets for that activity.
 * It also houses methods related to Activity (e.g., fetching fields).
 */
public class Activity implements Comparable<Activity> {
    private String activityName;
    private int ticketsAvailable;

    /**
     * constructs an Activity with given parameters
     * @param activityName name of activity
     * @param ticketsAvailable number of tickets available for that activity
     */
    public Activity(String activityName, int ticketsAvailable) {
        this.activityName = activityName;
        this.ticketsAvailable = ticketsAvailable;
    }

    /**
     * getter method. this returns the activity name for a given activity
     * @return activity name
     */
    public String getActivityName(){
        return activityName;
    }

    /**
     * setter method. this sets the activity name for a given activity.
     * as currently stands, the program does not allow the user to make changes to the activity name.
     * should functionality be expanded in the future, this will be a useful method.
     * @param activityName takes in new activity name.
     */
    public void setActivityName(String activityName) {
        this.activityName=activityName;
    }

    /**
     * getter method. this returns the number of available tickets for a given activity
     * @return number of available tickets for a given activity
     */
    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    /**
     * setter method. this sets the number of available tickets for a given activity.
     * it is used after buying or canceling tickets for an activity.
     * @param ticketsAvailable number of available tickets for a given activity
     */
    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable=ticketsAvailable;
    }

    /**
     * override default toString method
     * @return statement with number of tickets available for a given activity name
     */
    @Override
    public String toString() {
        if (ticketsAvailable == 1) {
            return ticketsAvailable + " ticket available for " + activityName + ".";
        } else
        return ticketsAvailable + " tickets available for " + activityName + ".";
    }

    /**
     * overrides default compareTo method
     * compares the names of the activities
     * it is case-insensitive
     * @param a the activity to be compared.
     * @return return 0 if the names are equal and -1 if they are inequal
     */
    @Override
    public int compareTo(Activity a) {
        int activityCompare = -1;
        if (activityName.equalsIgnoreCase(a.activityName)) {
            activityCompare = 0;
        }
        return activityCompare;
    }
}
