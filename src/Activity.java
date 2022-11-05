import java.util.ArrayList;

public class Activity {
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
    private static ArrayList<Activity> activityArrayList = new ArrayList<>();
    public static ArrayList<Activity> getActivityArrayList() {
        return activityArrayList;
    }
    public java.util.ArrayList<Activity> addActivity(Activity a) {
        activityArrayList.add(a);
        return activityArrayList;
    }
    @Override
    public String toString() {
        return ticketsAvailable + " tickets available for " + activityName + ".";
    }
}
