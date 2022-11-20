import java.util.ArrayList;

/**
 * Customer class stores information about the Customer object, namely the Customer name and number of orders the customer has placed.
 * It also houses methods related to Customer (e.g., fetching fields).
 */
public class Customer implements Comparable<Customer> {
    private String firstName;
    private String lastName;
    private int registeredOrders;

    /**
     * constructs a Customer with given parameters
     * @param name1 Customer's first name
     * @param name2 Customer's last name
     * @param numberOrders number of orders customer has placed
     */
    public Customer(String name1, String name2, int numberOrders) {
        firstName=name1;
        lastName=name2;
        registeredOrders=numberOrders;
    }

    /**
     * setter method. this sets the Customer's name
     * as currently stands, the program does not allow the user to make changes to the Customer's name.
     * should functionality be expanded in the future, this will be a useful method.
     * @param name1 Customer's first name
     * @param name2 Customer's last name
     */
    public void setName(String name1, String name2) {
        this.firstName=name1;
        this.lastName=name2;
    }

    /**
     * getter method
     * @return number of orders customer has placed
     */
    public int getRegisteredOrders(){
        return registeredOrders;
    }

    /**
     * setter method sets number of orders customer has placed
     * @param numberOrders number of orders customer has placed
     */
    public void setRegisteredOrders(int numberOrders){this.registeredOrders=numberOrders;}

    /**
     * getter for first name
     * @return customer's first name
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * getter for last name
     * @return customer's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * prints out information about customer
     * @return string indicating customer's name and number of registered activities
     */
    public String toString() {
        if (registeredOrders == 1) {
            return firstName + " " + lastName + " has registered for " + registeredOrders + " activity.";
        }
        else {
            return firstName + " " + lastName + " has registered for " + registeredOrders + " activities.";
        }
    }

    /**
     * compares two customers
     * both last name and first names must be the same in order to return a match
     * @param c the Customer object to be compared.
     * @return if the customers match, return zero. if not, return -1
     */
    public int compareTo(Customer c) {
        int compare = -1;
        if (lastName.equalsIgnoreCase(c.getLastName()) && firstName.equalsIgnoreCase(c.firstName)) compare = 0;
        return compare;
    }
}
