import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class TicketOffice {
    public TicketOffice() {
    }
    /*I suspect something like this will be needed. clerk should compare tickets requested by customer to tickets available for activity
    public boolean ticketsAvailable(){
        return (this.getTicketsAvailable()>ticketsRequested)
    }
     */
    private static SortedArrayList<Customer> customerList = new SortedArrayList<>();
    private static SortedArrayList<Activity> activityArrayList = new SortedArrayList<>();
    public static SortedArrayList<Customer> getCustomerList() {
        return customerList;
    }
    public SortedArrayList<Customer> addCustomer(Customer c) {
        customerList.add(c);
        return customerList;
    }
    public static SortedArrayList<Activity> getActivityArrayList() {
        return activityArrayList;
    }
    public SortedArrayList<Activity> addActivity(Activity a) {
        activityArrayList.add(a);
        return activityArrayList;
    }
    public static SortedArrayList<Activity> readActivitiesFile() {
        String text;
        int numberCustomers;
        int numberActivities = 0;
        SortedArrayList<Activity> activityArrayList = TicketOffice.getActivityArrayList();
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
    public static SortedArrayList<Customer> readCustomersFile() {
        int numberCustomers;
        int numberActivities = 0;
        SortedArrayList<Customer> customerList = TicketOffice.getCustomerList();
        try {
            Scanner inFile = new Scanner(new FileReader("input.txt"));
            while (inFile.hasNextLine()) {
                numberActivities = parseInt(inFile.nextLine());
                for (int i = 0; i < numberActivities; i++) {
                    inFile.nextLine(); //to skip over activity name
                    inFile.nextLine(); //to skip over tickets
                }

                numberCustomers = parseInt(inFile.nextLine());
                for (int j = 0; j < numberCustomers; j++) {
                    String name = inFile.nextLine();
                    String separated[] = name.split(" ");
                    String firstName = separated[0];
                    String lastName = separated[1];
                    Customer c = new Customer(firstName, lastName);
                    customerList.add(c);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, invalid file path.");
        }
        return customerList;
    }
}
