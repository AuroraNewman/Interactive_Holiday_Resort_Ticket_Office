import java.util.ArrayList;

public class Customer implements Comparable<Customer> {
    private String firstName;
    private String lastName;
    private int registeredActivities;

    public Customer() {
        firstName="";
        lastName="";
        registeredActivities=0;
    }
    public Customer(String name1, String name2, int numberTickets) {
        firstName=name1;
        lastName=name2;
        registeredActivities=numberTickets;
    }
    public void setName(String name1, String name2) {
        firstName=name1;
        lastName=name2;
    }
    public int getNumberRegisteredActivities(){ return registeredActivities;}
    public void setNumberRegisteredActivities(int numberTickets){registeredActivities=numberTickets;}
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String toString() {
        if (registeredActivities == 1) {
            return firstName + " " + lastName + " has registered for " + registeredActivities + " activity.";
        }
        else {
            return firstName + " " + lastName + " has registered for " + registeredActivities + " activities.";
        }
    }
    public int compareTo(Customer c) {
        int lastCompare = lastName.compareTo(c.lastName);
        if (lastCompare !=0) return lastCompare; //if first comes after second in comparison, return positive; if equal return zero; if second comes after first, return negative
        return firstName.compareTo(c.firstName);
    }
}
