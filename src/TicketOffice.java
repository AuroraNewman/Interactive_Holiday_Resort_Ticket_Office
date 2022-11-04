import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class TicketOffice {
    //make ticketoffice object and set equal to activity arraylist so we can act on it in the main
    private static ArrayList<Activity> activityArrayList = new ArrayList<>();
    public ArrayList<Activity> getActivityArrayList() {
        return activityArrayList;
    }
    public java.util.ArrayList<Activity> addActivity(Activity a) {
        activityArrayList.add(a);
        return activityArrayList;
    }
    private static void readFile() {
        String text;
        int numberCustomers;
        int numberActivities = 0;
        try {
            Scanner inFile = new Scanner(new FileReader("input.txt"));
            while (inFile.hasNextLine()) {
                numberActivities=parseInt(inFile.nextLine());
                for (int i=0; i<numberActivities; i++) {
                    String activityName = inFile.nextLine();
                    int ticketsAvailable=parseInt(inFile.nextLine());
                    Activity activity1 = new Activity(activityName, ticketsAvailable);
                    activityArrayList.add(activity1);
                }
                numberCustomers=parseInt(inFile.nextLine());
                for (int j=0; j<numberCustomers; j++) {
                    //text = inFile.next();
                    //customers.add(inFile.nextLine());
                    //make this one make a customer
                }
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Sorry, invalid file path.");
        }
    }
}
