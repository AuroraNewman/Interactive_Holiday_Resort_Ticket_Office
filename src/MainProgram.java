import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class MainProgram {
    //private static Activity activity1 = new Activity();
    static final int MAX_NUMBER_TICKETS_ALLOWED = 3;
    private static SortedArrayList<Activity> activities;
    private static SortedArrayList<Customer> customers;

    public static void main(String[] args) {
        TicketOffice.readActivitiesFile();
        activities = TicketOffice.getActivityArrayList();
        activities.sortArrayList(activities);
        TicketOffice.readCustomersFile();
        customers = TicketOffice.getCustomerList();
        customers.sortArrayList(customers);
        for (Customer cus : customers) {
            System.out.println(cus);
        }

        boolean done = false;
        while (!done) {
            Customer c = new Customer();
            String ticketActivityName = new String();
            int ticketsBought;
            TicketOffice ticket = new TicketOffice();
            printMenu();
            try {
                Scanner userInput = new Scanner(System.in);
                String option = String.valueOf(userInput.next().charAt(0));
                switch (option) {
                    case "f": //User exits the program.
                        done = true;
                        break;
                    case "a": //Display activity information.
                        for (Activity a : activities)
                            System.out.println(a);
                        break;
                    case "c": //Display customer information.
                        for (Customer cust : customers)
                            System.out.println(cust);
                        break;
                    case "t": //Update info after clerk sells ticket.
                        c = checkCustomer();
                        ticketActivityName = checkActivity();
                        ticketsBought = checkTicketQuantity(ticketActivityName);
                        if (!checkNumberTicketsSold(c)){
                            System.out.println("Sorry, you have already chosen three activities.");
                            System.out.println("Please cancel an existing activity before choosing a new one");
                        } else {
                            ticket = new TicketOffice(c, ticketActivityName, ticketsBought);
                            ticket.addTicket(ticket);
                        }

                        //sellTicket();
                        break;
                    case "r": //Update info after ticket canceled.

                        break;
                    default:
                        System.out.println("Please enter a valid option.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please choose a valid option: f, a, c, t, or r.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Welcome to the Samsung Resort on Geoje.");
        System.out.println("Please choose from one of the following options");
        System.out.println("f: Exit the program.");
        System.out.println("a: Display information about all activities.");
        System.out.println("c: Display information about all registered customers.");
        System.out.println("t: Update stored data after customer buys a ticket.");
        System.out.println("r: Update stored data after customer cancels a ticket.");
    }
    private static Customer checkCustomer() {
        Customer c = new Customer();
        boolean match = false;
        try {
            System.out.println("Please enter the customer's full name, first and last.");
            Scanner input = new Scanner(System.in);
            String name = String.valueOf(input.nextLine());
            String separated[] = name.split(" ");
            String firstName = separated[0];
            String lastName = separated[1];
            c.setName(firstName, lastName);
            System.out.println("You have entered:");
            System.out.println("First Name: " + firstName + ". Last Name: " + lastName);
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid entry.");
        }
        for (Customer cust : customers) {
            if (cust.compareTo(c) == 0) {
                match = true;
            }
        }
        if (!match){
            System.out.println("This name does not belong to a registered customer. Please check the name and try again.");
        }
        return c;
    }

    private static String checkActivity() {
    String ticketActivityName = new String();
    boolean match = false;
    try {
        System.out.println("Please enter the customer's chosen activity.");
        Scanner input = new Scanner(System.in);
        ticketActivityName = String.valueOf(input.nextLine());
        for (int i = 0; i < activities.size(); i++) {
            if (ticketActivityName.compareTo(activities.get(i).getActivityName()) == 0) {
                match = true;
                System.out.println("You have chosen: " + ticketActivityName + ".");
            }
        }
        if (!match) {
            System.out.println("This is not on our list of activities. Please check the activity and try again.");
        }
    }
    catch(InputMismatchException e) {
        System.out.println("This is not on our list of activities. Please check the activity and try again.");
    }
    return ticketActivityName;
    }
    private static int checkTicketQuantity(String ticketActivityName) { //this one prints to letters.txt
        int availableTickets = 0;
        int ticketsBought = 0;
        for (int i = 0; i < activities.size(); i++) {
            if (ticketActivityName.compareTo(activities.get(i).getActivityName()) == 0) {
                availableTickets = activities.get(i).getTicketsAvailable();
            }
        }
        try {
            System.out.println("There are " + availableTickets + " tickets available for " + ticketActivityName + ".");
            System.out.println("Please enter the number of tickets to be bought.");
            Scanner input = new Scanner(System.in);
            ticketsBought = input.nextInt();
        }
        catch (InputMismatchException e) {
            System.out.println("Please enter a valid number. Thank you.");
        }
        if (ticketsBought > availableTickets) {
            System.out.println("Sorry, there are insufficient available tickets. Please choose another activity.");
            //write this to letters.txt
                }
        return ticketsBought;
    }
    private static boolean checkNumberTicketsSold(Customer c) {
        ArrayList<TicketOffice> ticketList = TicketOffice.getTicketOfficeArrayList();
        int existingTickets=0;
        for (TicketOffice tick : ticketList) {
            if (tick.getTicketCustomer()==c){
                existingTickets++;
            }
        }
        if (existingTickets<MAX_NUMBER_TICKETS_ALLOWED){
            return true;
        } else {
            return false;
        }
    }
    /*
    sellTicket();
     */
}
