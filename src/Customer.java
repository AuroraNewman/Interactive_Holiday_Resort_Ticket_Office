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
    public int compareTo(Customer c) {
        int lastCompare = lastName.compareTo(c.lastName);
        if (lastCompare !=0) return lastCompare; //if first comes after second in comparison, return positive; if equal return zero; if second comes after first, return negative
        int firstCompare = firstName.compareTo(c.firstName);
        if (firstCompare !=0) return firstCompare;
        else return 0; //not sure about this, but I need to return something
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
