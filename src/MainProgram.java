import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class MainProgram {
    //private static Activity activity1 = new Activity();
    public static void main(String[] args) {
        readActivitiesFile();
        for (Activity a : Activity.getActivityArrayList()) {
            System.out.println(a);
        }

        readCustomersFile();
        for (Customer c : Customer.getCustomerList()) {
            System.out.println(c);
        }
    }



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

    private static ArrayList<Customer> readCustomersFile() {
        int numberCustomers;
        int numberActivities = 0;
        ArrayList<Customer> customerList = Customer.getCustomerList();
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