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
    @Override
    public int compareTo(Activity a){
        int activityCompare = activityName.compareTo(a.activityName);
        return activityCompare;

    private static ArrayList<Activity> readActivitiesFile() {
        String text;
        int numberCustomers;
        int numberActivities = 0;
        ArrayList<Activity> activityArrayList = Activity.getActivityArrayList();
        try {
            Scanner inFile = new Scanner(new FileReader("input.txt"));
            while (inFile.hasNextLine()) {
                numberActivities = parseInt(inFile.nextLine());
                for (int i = 0; i < numberActivities; i++) {
                    String activityName = inFile.nextLine();
                    int ticketsAvailable = parseInt(inFile.nextLine());
                    Activity activity1 = new Activity(activityName, ticketsAvailable);
                    activityArrayList.add(activity1);
                }
                //try removing this bit
                numberCustomers = parseInt(inFile.nextLine());
                for (int j = 0; j < numberCustomers; j++) {
                    text = inFile.nextLine();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, invalid file path.");
        }
        return activityArrayList;
    }

}
