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
        return firstName.compareTo(c.firstName);
    }
}
