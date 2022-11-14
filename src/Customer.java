import java.util.ArrayList;

public class Customer implements Comparable<Customer> {
    private String firstName;
    private String lastName;
    private int registeredOrders;
/*
    public Customer() {
        firstName="";
        lastName="";
        registeredTickets=0;
    }

 */

    public Customer(String name1, String name2, int numberOrders) {
        firstName=name1;
        lastName=name2;
        registeredOrders=numberOrders;
    }
    public void setName(String name1, String name2) {
        this.firstName=name1;
        this.lastName=name2;
    }
    public int getRegisteredOrders(){
        return registeredOrders;
    }
    public void setRegisteredOrders(int numberOrders){this.registeredOrders=numberOrders;}
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String toString() {
        if (registeredOrders == 1) {
            return firstName + " " + lastName + " has registered for " + registeredOrders + " activity.";
        }
        else {
            return firstName + " " + lastName + " has registered for " + registeredOrders + " activities.";
        }
    }
    public int compareTo(Customer c) {
        int lastCompare = lastName.compareTo(c.getLastName());
        if (lastCompare !=0) return lastCompare; //if first comes after second in comparison, return positive; if equal return zero; if second comes after first, return negative
        return firstName.compareTo(c.getFirstName());
    }
}
