import java.util.ArrayList;

public class Customer implements Comparable<Customer> {
    private String firstName;
    private String lastName;
    public Customer() {
        firstName="";
        lastName="";
    }
    public Customer(String name1, String name2) {
        firstName=name1;
        lastName=name2;
    }
    public void setName(String name1, String name2) {
        firstName=name1;
        lastName=name2;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String toString() {
        return firstName + " " + lastName;
    }
    public int compareTo(Customer otherCustomer) {
        if (firstName.equals(otherCustomer.firstName) && lastName.equals(otherCustomer.lastName)){
            return 0;
        }
        else {
            return 1;
        }
    }
    private static ArrayList<Customer> customerList = new ArrayList<>();
    public static ArrayList<Customer> getCustomerList() {
        return customerList;
    }
    public ArrayList<Customer> addCustomer(Customer c) {
        customerList.add(c);
        return customerList;
    }
}
