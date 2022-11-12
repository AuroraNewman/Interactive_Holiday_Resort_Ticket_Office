import java.util.ArrayList;

public class Customer implements Comparable<Customer> {
    private String firstName;
    private String lastName;
    private int registeredTickets;
/*
    public Customer() {
        firstName="";
        lastName="";
        registeredTickets=0;
    }

 */

    public Customer(String name1, String name2, int numberTickets) {
        firstName=name1;
        lastName=name2;
        registeredTickets=numberTickets;
    }
    public void setName(String name1, String name2) {
        this.firstName=name1;
        this.lastName=name2;
    }
    public int getRegisteredTickets(){
        return registeredTickets;
    }
    public void setRegisteredTickets(int numberTickets){this.registeredTickets=numberTickets;}
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String toString() {
        if (registeredTickets == 1) {
            return firstName + " " + lastName + " has registered for " + registeredTickets + " activity.";
        }
        else {
            return firstName + " " + lastName + " has registered for " + registeredTickets + " activities.";
        }
    }
    public int compareTo(Customer c) {
        int lastCompare = lastName.compareTo(c.lastName);
        if (lastCompare !=0) return lastCompare; //if first comes after second in comparison, return positive; if equal return zero; if second comes after first, return negative
        return firstName.compareTo(c.firstName);
    }
}
