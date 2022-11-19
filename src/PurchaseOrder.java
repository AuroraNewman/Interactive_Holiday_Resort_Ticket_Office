public class PurchaseOrder {
    private Customer ticketCustomer;
    private String ticketActivityName;
    private int ticketsBought;
    public PurchaseOrder(Customer ticketCustomer, String ticketActivityName, int ticketsBought) {
        this.ticketCustomer=ticketCustomer;
        this.ticketActivityName=ticketActivityName;
        this.ticketsBought=ticketsBought;
    }
    public Customer getTicketCustomer() {
        return ticketCustomer;
    }
    public void setTicketCustomer(Customer activeCustomer){
        this.ticketCustomer=activeCustomer;
    }
    public String getTicketActivityName() {
        return ticketActivityName;
    }
    public void setTicketActivityName(String ticketActivityName){
        this.ticketActivityName= ticketActivityName;
    }

    public int getTicketsBought(){
        return ticketsBought;
    }
    public void setTicketsBought(int ticketsBought){
        this.ticketsBought= ticketsBought;
    }
    public static String toString(PurchaseOrder po){
        if (po.getTicketsBought()==1){
            return po.getTicketCustomer().getFirstName() + " " + po.getTicketCustomer().getLastName() + " has purchased " + po.getTicketsBought() + " ticket for the activity " + po.ticketActivityName + ".";

        } else {
            return po.getTicketCustomer().getFirstName() + " " + po.getTicketCustomer().getLastName() + " has purchased " + po.getTicketsBought() + " tickets for the activity " + po.ticketActivityName + ".";
        }
    }
    public int compareTo(PurchaseOrder purchaseOrder) {
        int nameCompare = getTicketCustomer().compareTo(purchaseOrder.getTicketCustomer());
        int activityCompare = getTicketActivityName().compareTo(purchaseOrder.getTicketActivityName());
        if (nameCompare == 0 && activityCompare == 0) {
            return nameCompare;
        } else if (nameCompare !=0) {
            return nameCompare;
        } else {
            activityCompare=-1;
            return activityCompare;
        }
    }
}
