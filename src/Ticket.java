public class Ticket {
    private Customer ticketCustomer;
    private String ticketActivityName;
    private int ticketsBought;
    /*
    public Ticket() {
        ticketCustomer = Customer("","",ticketsBought);
        ticketActivityName="";
        ticketsBought=0;
    }

     */
    public Ticket(Customer ticketCustomer, String ticketActivityName, int ticketsBought) {
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
    public static String toString(Ticket t){
        return t.getTicketCustomer().getFirstName() + " " + t.getTicketCustomer().getLastName() + " has purchased " + t.getTicketsBought() + " tickets for the activity " + t.ticketActivityName + ".";
    }
}
