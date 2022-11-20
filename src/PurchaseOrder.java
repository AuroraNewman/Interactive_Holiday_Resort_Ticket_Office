/**
 * Purchase Order class stores information about the Purchase Order object: Customer, activity name, and number of tickets purchased
 * It also houses methods related to the purchase order (e.g., fetching fields)
 */
public class PurchaseOrder {
    private Customer ticketCustomer;
    private String ticketActivityName;
    private int ticketsBought;

    /**
     * constructs a Purchase Order with given parameters
     * @param ticketCustomer customer who purchased the order
     * @param ticketActivityName activity purchased
     * @param ticketsBought number of tickets purchased
     */
    public PurchaseOrder(Customer ticketCustomer, String ticketActivityName, int ticketsBought) {
        this.ticketCustomer=ticketCustomer;
        this.ticketActivityName=ticketActivityName;
        this.ticketsBought=ticketsBought;
    }

    /**
     * getter method
     * @return customer who placed the order
     */
    public Customer getTicketCustomer() {
        return ticketCustomer;
    }
    /**
     * setter method. this sets the Customer's name on the ticket
     * as currently stands, the program does not allow the user to make changes to the Customer.
     * should functionality be expanded in the future, this will be a useful method.
     * @param activeCustomer customer who placed the order
     */
    public void setTicketCustomer(Customer activeCustomer){
        this.ticketCustomer=activeCustomer;
    }
    /**
     * getter method. this returns the activity name for a given activity
     * @return activity name
     */
    public String getTicketActivityName() {
        return ticketActivityName;
    }
    /**
     * setter method. this sets the activity name for a given activity.
     * as currently stands, the program does not allow the user to make changes to the activity name.
     * should functionality be expanded in the future, this will be a useful method.
     * @param ticketActivityName takes in new activity name.
     */
    public void setTicketActivityName(String ticketActivityName){
        this.ticketActivityName= ticketActivityName;
    }
    /**
     * getter method. this returns the number of tickets purchased for a given activity
     * @return number of tickets purchased for a given activity
     */
    public int getTicketsBought(){
        return ticketsBought;
    }
    /**
     * setter method. this sets the number of tickets purchased for a given activity.
     * it is used after buying or canceling tickets for an activity.
     * @param ticketsBought number of tickets purchased for a given activity.
     */
    public void setTicketsBought(int ticketsBought){
        this.ticketsBought= ticketsBought;
    }

    /**
     * prints out information on a purchase order in a useful and legible manner
     * @param po takes in purchase order for which we want information
     * @return string saying how many tickets customer purchased for an activity
     */
    public static String toString(PurchaseOrder po){
        if (po.getTicketsBought()==1){
            return po.getTicketCustomer().getFirstName() + " " + po.getTicketCustomer().getLastName() + " has purchased " + po.getTicketsBought() + " ticket for the activity " + po.ticketActivityName + ".";

        } else {
            return po.getTicketCustomer().getFirstName() + " " + po.getTicketCustomer().getLastName() + " has purchased " + po.getTicketsBought() + " tickets for the activity " + po.ticketActivityName + ".";
        }
    }
    /**
     * compares the purchase orders
     * specifically, it compares the names of the customers (first and last) as well as the activity names
     * if any of those three are inequal, it returns -1
     * if all three are equal, zero is returned.
     * it is case-insensitive
     * @param purchaseOrder the purchase order to be compared.
     * @return return 0 if the Customers and activity names are both equal and -1 if any are inequal
     */
    public int compareTo(PurchaseOrder purchaseOrder) {
        int compare = 0;
        if (!getTicketCustomer().getFirstName().equalsIgnoreCase(purchaseOrder.ticketCustomer.getFirstName())){
            compare = -1;
            return compare;
        }
        if (!getTicketCustomer().getLastName().equalsIgnoreCase(purchaseOrder.ticketCustomer.getLastName())){
            compare = -1;
            return compare;
        }
        if (!getTicketActivityName().equalsIgnoreCase(purchaseOrder.getTicketActivityName())){
            compare = -1;
            return compare;
        }
        return compare;
    }
}
