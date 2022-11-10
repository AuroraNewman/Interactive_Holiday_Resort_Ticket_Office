import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class MainProgram {
    //private static Activity activity1 = new Activity();
    static final int MAX_NUMBER_TICKETS_ALLOWED = 3;
    private static SortedArrayList<Activity> activities;
    private static SortedArrayList<Customer> customers;
    //private static Customer c = new Customer();
    //private static String ticketActivityName = new String();
    //private static int ticketsBought; //number of tickets customer bought for activity
    //private static int registeredActivities; //number of activities customer has booked


    public static void main(String[] args) {
        TicketOffice.readActivitiesFile();
        activities = TicketOffice.getActivityArrayList();
        activities.sortArrayList(activities);
        TicketOffice.readCustomersFile();
        customers = TicketOffice.getCustomerList();
        customers.sortArrayList(customers);

        boolean done = false;
        while (!done) {
            //Ticket activeTicket = new Ticket(); moving to case(t)
            //Customer activeCustomer = new Customer();  moving to case(t)
            //int registeredActivities = activeCustomer.getNumberRegisteredActivities();
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
                        boolean nameCheck = false;
                        boolean numberActivitiesCheck = false;
                        boolean activityCheck = false;
                        boolean ticketNumberCheck = false;
                        boolean checkSuccessful = false;
                        Ticket activeTicket = new Ticket();
                        Customer activeCustomer = new Customer();
                        try {
                            System.out.println("Please enter the customer's full name, first and last.");
                            Scanner input = new Scanner(System.in);
                            String name = input.nextLine();
                            String separated[] = name.split(" ");
                            String firstName = separated[0];
                            String lastName = separated[1];
                            activeCustomer.setName(firstName, lastName);
                            System.out.println("You have entered:");
                            System.out.println("First Name: " + firstName + ". Last Name: " + lastName);
                            if (checkName(activeCustomer)) {
                                activeCustomer.setName(firstName, lastName);
                                nameCheck = true;
                            } else if (!checkName(activeCustomer)) {
                                System.out.println("You have entered an invalid name. Please try again.");
                                break;
                            }
                            if (checkNumberActivities(activeCustomer)) {
                                int registeredActivities = activeCustomer.getNumberRegisteredActivities();
                                registeredActivities++;
                                activeCustomer.setNumberRegisteredActivities(registeredActivities);
                                activeTicket.setTicketCustomer(activeCustomer);
                                numberActivitiesCheck = true;
                            } else if (!checkNumberActivities(activeCustomer)){
                                System.out.println(activeCustomer.getFirstName() + " " + activeCustomer.getLastName() + " has registered for too many activities.");
                                System.out.println("Please cancel an activity before adding a new one.");
                                break;
                            }
                            System.out.println("Please enter the customer's chosen activity.");
                            String ticketActivityName = input.nextLine();
                            if (checkActivities(ticketActivityName)) {
                                activeTicket.setTicketActivityName(ticketActivityName);
                                activityCheck = true;
                            } else if (!checkActivities(ticketActivityName)) {
                                System.out.println(ticketActivityName + " is not a valid activity name. Please try again.");
                                break;
                            }
                            System.out.println("Please enter the number of tickets to be bought.");
                            int ticketsBought = input.nextInt();
                            if (checkSufficientTickets(ticketActivityName, ticketsBought)) {
                                activeTicket.setTicketsBought(ticketsBought);
                                ticketNumberCheck = true;
                            } else if (!checkSufficientTickets(ticketActivityName, ticketsBought)) {
                                System.out.println("There are not enough tickets available. Please choose a different activity or quantity.");
                                break;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Please enter a valid option.");
                        }
                        if (nameCheck && numberActivitiesCheck && activityCheck && ticketNumberCheck) {
                            checkSuccessful = true;
                        }
                        if (!checkSuccessful) {
                            System.out.println("Please try again. Thank you.");
                            break;
                        }
                        //store ticket in ticket office
                        boolean updateComplete = update(activeTicket);
                        System.out.println("update complete? "+ updateComplete);
                        System.out.println(activeCustomer.getFirstName() + " " + activeCustomer.getLastName() + " has bought " + activeTicket.getTicketsBought() + " for " + activeTicket.getTicketActivityName());
                        System.out.println(activeCustomer.getFirstName() + " " + activeCustomer.getLastName() + " has registered for " + activeCustomer.getNumberRegisteredActivities() + " activities.");
                        break;
                        /* new stuff from airport
                        registeredActivities = update(ticket1, ticketsBought);
                        System.out.println("tickets bought after running update in main: " + c.getNumberTickets());
                        c.setNumberTickets(registeredActivities);
                        if (c.getNumberTickets() == 1) {
                            System.out.println(c.getFirstName() + " " + c.getLastName() + " has registered for " + c.getNumberTickets() + " activity.");
                        } else {
                            System.out.println(c.getFirstName() + " " + c.getLastName() + " has registered for " + c.getNumberTickets() + " activities.");
                        }
                        break;

                         */
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

    private static boolean checkName(Customer activeCustomer){
        boolean matchCustomer = false;
        //check customer match
        for (Customer cust : customers) {
            if (cust.compareTo(activeCustomer) == 0) {
                matchCustomer = true;
                System.out.println(cust.getFirstName() + " " + cust.getLastName() + " is a valid name.");
            }
        }
        if (!matchCustomer) {
            System.out.println("This name does not belong to a registered customer. Please check the name and try again.");
        }
        return matchCustomer;
    }
    //check customer has registered for 3 or fewer activities
    private static boolean checkNumberActivities(Customer activeCustomer){
        int registeredTickets = activeCustomer.getNumberRegisteredActivities();
        System.out.println(activeCustomer.getFirstName() + " " + activeCustomer.getLastName() + " has registered for " + registeredTickets + " tickets.");
        boolean canRegister = false;
        if (activeCustomer.getNumberRegisteredActivities()<MAX_NUMBER_TICKETS_ALLOWED){
            canRegister = true;
        }
        return canRegister;
    }

    private static boolean checkActivities(String ticketActivityName){
        boolean matchActivity = false;
        for (int i = 0; i < activities.size(); i++) {
            if (ticketActivityName.compareTo(activities.get(i).getActivityName()) == 0) {
                System.out.println("You have chosen: " + ticketActivityName + ".");
                matchActivity=true;
            }
        }
        if (!matchActivity) {
            System.out.println("This is not on our list of activities. Please check the activity and try again.");
        }
        return matchActivity;
    }

    //check sufficient tickets
    private static boolean checkSufficientTickets(String ticketActivityName, int ticketsBought) {
        int availableTickets = 0;
        boolean ticketsAvailable = false;
        for (int i = 0; i < activities.size(); i++) {
            if (ticketActivityName.compareTo(activities.get(i).getActivityName()) == 0) {
                availableTickets = activities.get(i).getTicketsAvailable();
            }
        }
        if (ticketsBought > availableTickets) {
            System.out.println("Sorry, there are insufficient available tickets. Please choose another activity.");
            return ticketsAvailable;
            //write this to letters.txt
        } else if (ticketsBought == 1) {
            System.out.println("You have purchased " + ticketsBought + " ticket for " + ticketActivityName + ".");
            ticketsAvailable = true;
            return ticketsAvailable;
        } else if (ticketsBought > 1) {
            System.out.println("You have purchased " + ticketsBought + " tickets for " + ticketActivityName + ".");
            ticketsAvailable = true;
            return ticketsAvailable;
        } else {
            System.out.println("Please enter a valid number of tickets. Thank you.");
            return ticketsAvailable;
        }
    }
    private static boolean update(Ticket t){
        boolean updateComplete = false;
        Customer c = t.getTicketCustomer();
        String ticketActivityName =t.getTicketActivityName();
        //int numberRegisteredTickets = c.getNumberRegisteredActivities(); updating this in main using method 1 now
        //System.out.println("numberRegisteredTickets in update method before adding: " + numberRegisteredTickets);
        //update the available tickets for the activity:
        for (int i = 0; i < activities.size(); i++) {
            if (ticketActivityName.compareTo(activities.get(i).getActivityName()) == 0) {
                Activity a = activities.get(i);
                int ticketsAvailable = a.getTicketsAvailable();
                ticketsAvailable = ticketsAvailable - t.getTicketsBought();
                a.setTicketsAvailable(ticketsAvailable);
                System.out.println("Remaining available tickets: " + ticketsAvailable);
            }
        }
        /*
        //update number of activities for which customer has registered
        //method 1 (currently in use: update after the boolean checkActivities method
        //method 2. for loop
        for (int i = 0; i<customers.size(); i++){
            if (c.compareTo(customers.get(i))==0){
                numberRegisteredTickets = c.getNumberRegisteredActivities();
                numberRegisteredTickets ++;
                c.setNumberRegisteredActivities(numberRegisteredTickets);
            }
        }
        method 3: pull number tickets from customer, add 1, set it
        int numberTicketsBought = c.getRegisteredTickets();
        numberTicketsBought ++;
        c.setRegisteredTickets(numberTicketsBought);


        System.out.println("numberTicketsBought in update method after adding: " + numberRegisteredTickets);
        c.setNumberRegisteredActivities(numberRegisteredTickets);
        System.out.println("numberTicketsBought in update method after setting: " + numberRegisteredTickets);

         */
        updateComplete = true;
        return updateComplete;
    }
}
//TO DO: the number of registered activities  is not increasing. no changes made to the customer are saving
//TO DO: method checkTicketQuantity should write to the letters text file
//TO DO: if someone mistypes a name, they still have to enter the activity and #tickets before returning to the main menu.