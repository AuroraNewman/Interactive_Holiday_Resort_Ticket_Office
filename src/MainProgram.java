import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class MainProgram {
    //private static Activity activity1 = new Activity();
    private static SortedArrayList<Activity> activities;
    private static SortedArrayList<Customer> customers;
    public static void main(String[] args) {
        readActivitiesFile();
        for (Activity a : TicketOffice.getActivityArrayList()) {
            System.out.println(a);
        }

        activities = TicketOffice.getActivityArrayList();
        readCustomersFile();
        for (Customer c : TicketOffice.getCustomerList()) {
            System.out.println(c);
        }
        customers = TicketOffice.getCustomerList();
        SortedArrayList<String> test = new SortedArrayList<>();
        test.add("file");
        test.add("song");
        test.add("alphabet");
        test.add("Terrible");
        test.add("tenerife");
        test.add("per che");
        //Customer C = new Customer ("Alfred", "String");

        customers.sortArrayList(customers);
        for (Customer c : customers) {
            System.out.println(c);
        }


        test.sortArrayList(test);
        for (Object palabra: test) {
            System.out.println(palabra);
        }
        activities.sortArrayList(activities);
        for (Activity a : activities) {
            System.out.println(a);
        }

    }

    private static SortedArrayList<Activity> readActivitiesFile() {
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

    private static SortedArrayList<Customer> readCustomersFile() {
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