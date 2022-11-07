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
}
