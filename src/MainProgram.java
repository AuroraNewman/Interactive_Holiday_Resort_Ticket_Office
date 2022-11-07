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
        TicketOffice.readActivitiesFile();
        activities = TicketOffice.getActivityArrayList();
        customers = TicketOffice.getCustomerList();
        /* this is just checking everything got added properly and that the sorting works.
        for (Activity a : TicketOffice.getActivityArrayList()) {
            System.out.println(a);
        }
        TicketOffice.readCustomersFile();
        for (Customer c : TicketOffice.getCustomerList()) {
            System.out.println(c);
        }
        customers.sortArrayList(customers);
        for (Customer c : customers) {
            System.out.println(c);
        }
        activities.sortArrayList(activities);
        for (Activity a : activities) {
            System.out.println(a);
        }

         */

    }
}