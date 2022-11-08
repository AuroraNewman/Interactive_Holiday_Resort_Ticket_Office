public class Ticket {
    private Customer ticketCustomer;
    private String ticketActivityName;
    private int ticketsBought;
    public Ticket() {
        ticketCustomer = new Customer("","",ticketsBought);
        ticketActivityName="";
        //ticketsBought=0;
    }
    public Ticket(Customer ticketCustomer, String ticketActivityName, int ticketsBought) {
        this.ticketCustomer=ticketCustomer;
        this.ticketActivityName=ticketActivityName;
        this.ticketsBought=ticketsBought;
    }
    public Customer getTicketCustomer() {
        return ticketCustomer;
    }
    public void setTicketCustomer(){
        this.ticketCustomer=ticketCustomer;
    }
    public String getTicketActivityName() {
        return ticketActivityName;
    }
    public void setTicketActivityName(){
        this.ticketActivityName=ticketActivityName;
    }
    /*
    public int getTicketsBought(){
        return ticketsBought;
    }
    public void setTicketsBought(){
        this.ticketsBought=ticketsBought;
    }

     */
}
