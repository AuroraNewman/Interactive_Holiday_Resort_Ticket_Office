import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class MainProgram {
    //private static Activity activity1 = new Activity();
    static final int MAX_NUMBER_TICKETS_ALLOWED = 3;
    private static SortedArrayList<Activity> activities;
    private static SortedArrayList<Customer> customers;

    public static void main(String[] args) {
        TicketOffice.readActivitiesFile();
        activities = TicketOffice.getActivityArrayList();
        activities.sortArrayList(activities);
        TicketOffice.readCustomersFile();
        customers = TicketOffice.getCustomerList();
        customers.sortArrayList(customers);
    }
}
//TO DO: the number of registered activities  is not increasing. no changes made to the customer are saving
//TO DO: method checkTicketQuantity should write to the letters text file
//TO DO: if someone mistypes a name, they still have to enter the activity and #tickets before returning to the main menu.