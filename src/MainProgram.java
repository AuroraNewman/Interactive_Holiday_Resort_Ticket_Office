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

        boolean done = false;
        while (!done) {
            Customer c = new Customer();
            String ticketActivityName = new String();
            int ticketsBought;
            //TicketOffice ticket = new TicketOffice();
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
                        //boolean canBuy = checkNumberTicketsSold(c);
                        c = checkCustomer();
                        ticketActivityName = checkActivity();
                        ticketsBought = checkTicketQuantity(ticketActivityName);
                        Ticket ticket1;
                        if (checkNumberTicketsSold(c)) {
                            ticket1 = new Ticket(c, ticketActivityName, ticketsBought);
                            TicketOffice.getListOfTickets().add(ticket1);
                        } else {
                            System.out.println("Sorry, you have already chosen three activities.");
                            System.out.println("Please cancel an existing activity before choosing a new one");
                            break;
                        }
                        update(ticket1, ticketsBought);
                        if (c.getNumberTickets() == 1) {
                            System.out.println(c.getFirstName() + " " + c.getLastName() + " has registered for " + c.getNumberTickets() + " activity.");
                        } else {
                            System.out.println(c.getFirstName() + " " + c.getLastName() + " has registered for " + c.getNumberTickets() + " activities.");
                        }
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
        System.out.println("Please choose from one of the following options.");
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
        catch (IndexOutOfBoundsException f) {
            System.out.println("Please enter a valid name for a registered customer.");
        }
        for (Customer cust : customers) {
            if (cust.compareTo(c) == 0) {
                match = true;
            }
        }
        if (!match) {
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
        } catch (InputMismatchException e) {
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
            if (ticketsBought == 1) {
                System.out.println("You have purchased " + ticketsBought + " ticket for " + ticketActivityName + ".");
            } else if (ticketsBought > 1) {
                System.out.println("You have purchased " + ticketsBought + " tickets for " + ticketActivityName + ".");
            } else {
                System.out.println("Please enter a valid number of tickets. Thank you.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid number. Thank you.");
        }
        if (ticketsBought > availableTickets) {
            System.out.println("Sorry, there are insufficient available tickets. Please choose another activity.");
            //write this to letters.txt
        }
        return ticketsBought;
    }
    //this checks that the customer has not registered for too many activities
    private static boolean checkNumberTicketsSold(Customer c) {
        ArrayList<Ticket> ticketList = TicketOffice.getListOfTickets();
        int existingTickets = c.getNumberTickets();
        System.out.println(existingTickets);
        //boolean tickPossible = false;
        /*
        //this generates the var existing tickets each time instead of pulling it from customer
        for (Ticket t : ticketList) {
            if (t.getTicketCustomer().compareTo(c) == 0) {
                existingTickets++;
                tickPossible = true;
            } else {
                tickPossible = false;
            }
        }
        return tickPossible;

         */

        //check if customer is registered for too many activities

        if (existingTickets < MAX_NUMBER_TICKETS_ALLOWED) {
            //existingTickets++;
            //c.setNumberTickets(existingTickets);
            c.setName("alpha", "bet");
            return true;
        } else {
            return false;
        }
    }
    private static boolean update(Ticket t, int ticketsBought){
        boolean updateComplete = false;
        Customer c = t.getTicketCustomer();
        String ticketActivityName =t.getTicketActivityName();
        //update the available tickets for the activity:
        for (int i = 0; i < activities.size(); i++) {
            if (ticketActivityName.compareTo(activities.get(i).getActivityName()) == 0) {
                Activity a = activities.get(i);
                int ticketsAvailable = a.getTicketsAvailable();
                ticketsAvailable = ticketsAvailable - ticketsBought;
                a.setTicketsAvailable(ticketsAvailable);
                System.out.println("Remaining available tickets: " + ticketsAvailable);
            }
        }
        //update number of activities for which customer has registered
        int numberTicketsBought = c.getNumberTickets();
        numberTicketsBought ++;
        c.setNumberTickets(numberTicketsBought);
        return updateComplete;
    }
}
//TO DO: the number of registered activities  is not increasing. no changes made to the customer are saving
//TO DO: method checkTicketQuantity should write to the letters text file
//TO DO: if someone mistypes a name, they still have to enter the activity and #tickets before returning to the main menu.