import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class MainProgram {
    static final int MAX_NUMBER_ACTIVITIES_ALLOWED = 3;
    private static SortedArrayList<Activity> activities;
    //private static ArrayList<Ticket> tickets;

    public static void main(String[] args) {
        TicketOffice.readActivitiesFile();
        activities = TicketOffice.getActivityArrayList();
        SortedArrayList.sortArrayList(activities);
        TicketOffice.readCustomersFile();
        SortedArrayList.sortArrayList(TicketOffice.getCustomerList());

        boolean done = false;
        Customer activeCustomer = new Customer("", "", 0);
        String ticketActivityName = "";
        int ticketQuantity = 0;
        Ticket activeTicket = new Ticket(activeCustomer, ticketActivityName, ticketQuantity);
        while (!done) {
            int ticketsBought = 0;
            printMenu();
            try {
                Scanner userInput = new Scanner(System.in);
                char option = userInput.next().charAt(0);
                //to make it only accept correct answer, make it .equals to the various options or else goodbye
                switch (option) {
                    case 'f': //User exits the program.
                    case 'F':
                        done = true;
                        break;
                    case 'a': //Display activity information.
                    case 'A':
                        for (Activity a : activities)
                            System.out.println(a);
                        break;
                    case 'c': //Display customer information.
                    case 'C':
                        for (Customer cust : TicketOffice.getCustomerList())
                            System.out.println(cust);
                        break;
                    case 't': //Update info after clerk sells ticket.
                    case 'T':
                        boolean tComplete = false;
                        while (!tComplete) {
                            boolean nameCheck = false;
                            boolean numberActivitiesCheck = false;
                            boolean activityCheck = false;
                            boolean ticketNumberCheck = false;
                            //boolean checkSuccessful = false;
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
                                    nameCheck = true;
                                } else if (!checkName(activeCustomer)) {
                                    System.out.println("This name is invalid. Please try again.");
                                    break;
                                }
                                if (checkNumberActivities(activeCustomer)) {
                                    numberActivitiesCheck = true;
                                } else if (!checkNumberActivities(activeCustomer)) {
                                    System.out.println(activeCustomer.getFirstName() + " " + activeCustomer.getLastName() + " has registered for too many activities.");
                                    System.out.println("Please cancel an activity before adding a new one.");
                                    break;
                                }
                                System.out.println("Please enter the customer's chosen activity.");
                                ticketActivityName = input.nextLine();
                                if (checkActivities(ticketActivityName)) {
                                    activityCheck = true;
                                } else if (!checkActivities(ticketActivityName)) {
                                    System.out.println(ticketActivityName + " is not a valid activity name. Please try again.");
                                    break;
                                }
                                System.out.println("Please enter the number of tickets to be bought.");
                                ticketsBought = input.nextInt();
                                if (checkSufficientTickets(ticketActivityName, ticketsBought)) {
                                    ticketNumberCheck = true;
                                } else if (!checkSufficientTickets(ticketActivityName, ticketsBought)) {
                                    System.out.println("There are not enough tickets available. Please choose a different activity or quantity.");
                                    break;
                                }
                                if (nameCheck && numberActivitiesCheck && activityCheck && ticketNumberCheck) {
                                    activeTicket.setTicketCustomer(activeCustomer);
                                    activeTicket.setTicketActivityName(ticketActivityName);
                                    activeTicket.setTicketsBought(ticketsBought);
                                    System.out.println(Ticket.toString(activeTicket));
                                    //TicketOffice.getListOfTickets().add(activeTicket);
                                    boolean updateComplete = update(activeTicket, ticketsBought);
                                    System.out.println(activeCustomer.getFirstName() + " " + activeCustomer.getLastName() + " has registered for " + activeCustomer.getRegisteredTickets() + " activities.");
                                    tComplete = true;
                                    //checkSuccessful = true;
                                } else {
                                    System.out.println("Please try again. Thank you.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Please enter a valid option.");
                            }
                            catch (IndexOutOfBoundsException f) {
                                System.out.println("Please check your input and try again. Thank you.");
                            }
                                break;
                            }
                        break;
                    case 'r': //Update info after ticket canceled.
                    case 'R':
                        boolean rComplete = false;
                        while (!rComplete) {
                            boolean nameCheck = false;
                            boolean numberActivitiesCheck = false;
                            boolean activityCheck = false;
                            int cancelQuantity;
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
                                //check customer's name is valid
                                if (checkName(activeCustomer)) {
                                    nameCheck = true;
                                } else if (!checkName(activeCustomer)) {
                                    System.out.println("You have entered an invalid name. Please try again.");
                                    break;
                                }
                                for (Customer c : TicketOffice.getCustomerList()){
                                    if (activeCustomer.compareTo(c)==0){
                                     activeCustomer=c;
                                    }
                                }
                                //check activity name is valid.
                                System.out.println("Please enter the activity to be cancelled.");
                                ticketActivityName = input.nextLine();
                                if (checkActivities(ticketActivityName)) {
                                    activityCheck = true;
                                } else if (!checkActivities(ticketActivityName)) {
                                    System.out.println(ticketActivityName + " is not a valid activity name. Please try again.");
                                    break;
                                }
                                //find ticket using customer name and activity name
                                //check the ticket has the same customer and activity names as input
                                ArrayList<Ticket> tickets = TicketOffice.getListOfTickets();
                                for (Ticket t : tickets) {
                                    int nameMatch = t.getTicketCustomer().compareTo(activeCustomer);
                                    if (nameMatch == 0) {
                                        int activityMatch = t.getTicketActivityName().compareTo(ticketActivityName);
                                        if (activityMatch == 0) {
                                            activeTicket = t;
                                        }
                                    }
                                }
                                //ask how many tickets to cancel
                                try {
                                    System.out.println("You have purchased " + activeTicket.getTicketsBought() + " tickets.");
                                    System.out.println("How many tickets will you cancel?");
                                    cancelQuantity = input.nextInt();
                                    cancelQuantity = cancelQuantity * (-1);
                                    if (cancelQuantity <= activeTicket.getTicketsBought()) {
                                        update(activeTicket, cancelQuantity);
                                        //reduce number of registered activities
                                    } else {
                                        System.out.println("You have requested a cancellation for more tickets than are available.");
                                        System.out.println("Please affirm the quantity and try again.");
                                        break; //note, may change this section to a while loop. have boolean set at top and this way clerk doesn't have to run through all the toptions again
                                    }

                                } catch (InputMismatchException e) {
                                    System.out.println("Please check your input and try again.");
                                }
                            } catch  (InputMismatchException e) {
                                    System.out.println("Please enter a valid option.");
                                }
                            catch (IndexOutOfBoundsException f) {
                                System.out.println("Please check your input and try again. Thank you.");
                            }
                        rComplete = true;
                        }
                        break;
                    default:
                        System.out.println("Please enter a valid option.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please choose a valid option: f, a, c, t, or r.");
            } catch(IndexOutOfBoundsException f){
                System.out.println("Please enter a valid option.");
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
        for (Customer cust : TicketOffice.getCustomerList()) {
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
        int registeredActivities = activeCustomer.getRegisteredTickets();
        System.out.println(activeCustomer.getFirstName() + " " + activeCustomer.getLastName() + " has registered for " + registeredActivities + " activities.");
        boolean canRegister = false;
        if (activeCustomer.getRegisteredTickets()<MAX_NUMBER_ACTIVITIES_ALLOWED){
            canRegister = true;
        }
        return canRegister;
    }
    //check activity match
    private static boolean checkActivities(String ticketActivityName){
        boolean matchActivity = false;
        for (int i = 0; i < activities.size(); i++) {
            if (ticketActivityName.compareTo(activities.get(i).getActivityName()) == 0) {
                System.out.println("You have chosen: " + ticketActivityName + ".");
                matchActivity=true;
            }
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
        boolean checkComplete = false;
        while (!checkComplete) {
            if (ticketsBought > availableTickets) {
                print(ticketActivityName, ticketsBought, availableTickets);
                checkComplete = true;
                return ticketsAvailable;
            } else if (ticketsBought == 1) {
                ticketsAvailable = true;
                checkComplete = true;
                return ticketsAvailable;
            } else if (ticketsBought > 1) {
                ticketsAvailable = true;
                checkComplete = true;
                return ticketsAvailable;
            } else {
                System.out.println("Please enter a valid number of tickets. Thank you.");
                checkComplete = true;
                return ticketsAvailable;
            }
        }
        return ticketsAvailable;
    }
    private static void print(String ticketActivityName, int ticketsBought, int availableTickets){
        PrintWriter outFile;
            try {
                outFile = new PrintWriter("letters.txt");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        outFile.write("A customer attempted to buy " + ticketsBought + " tickets for " + ticketActivityName + ", but only " + availableTickets + " tickets are available.");
        outFile.close();
    }
    /* this method attempts to take into consideration whether or not a ticket has been bought by this customer for this activity.
    since modifying it, neither the number of tickets nor the number of registered activities changes.
    original version below

     */

    private static boolean update(Ticket t, int ticketsBought){
        /* printing here proves that the update method is being accessed and that the comparison works
        System.out.println("6: here is the update method");
        Customer tempc = new Customer("a", "a", 0);
        Ticket tempt = new Ticket(tempc, "a", 2);
        int tempComp = tempt.compareTo(t);
        System.out.println(tempComp);
         */
        boolean updateComplete = false;
        Customer c = t.getTicketCustomer();
        String ticketActivityName =t.getTicketActivityName();
        ArrayList<Ticket> tickets =  TicketOffice.getListOfTickets();
        if (tickets.size()>0) {
            System.out.println("ticketsize not zero");
            for (Ticket compareTicket : tickets) {
                int comparisonInteger = compareTicket.compareTo(t);
                System.out.println("5: " + comparisonInteger);
                if (comparisonInteger == 0) {
                    //update tickets on customer's ticket to reflect more tickets bought
                    int previousTicketQuantity = t.getTicketsBought();
                    previousTicketQuantity += ticketsBought;
                    t.setTicketsBought(previousTicketQuantity);
                    //update available tickets in activity list
                    for (int i = 0; i < activities.size(); i++) {
                        if (ticketActivityName.compareTo(activities.get(i).getActivityName()) == 0) {
                            System.out.println("3: " + ticketActivityName);
                            Activity a = activities.get(i);
                            int ticketsAvailable = a.getTicketsAvailable();
                            ticketsAvailable -= ticketsBought;
                            System.out.println("4: " + ticketsAvailable);
                            a.setTicketsAvailable(ticketsAvailable);
                            System.out.println("Remaining tickets available for " + ticketActivityName + ": " + ticketsAvailable);
                            updateComplete = true;
                            //return updateComplete;
                        }
                    }
                } else if (comparisonInteger != 0) { //if this is a new activity for the customer
                    for (int i = 0; i < activities.size(); i++) {
                        if (ticketActivityName.compareTo(activities.get(i).getActivityName()) == 0) {
                            System.out.println("1: " + ticketActivityName);
                            Activity a = activities.get(i);
                            int ticketsAvailable = a.getTicketsAvailable();
                            ticketsAvailable -= ticketsBought;
                            System.out.println("2: " + ticketsAvailable);
                            a.setTicketsAvailable(ticketsAvailable);
                            System.out.println("Remaining tickets available for " + ticketActivityName + ": " + ticketsAvailable);
                            TicketOffice.getListOfTickets().add(t);
                        }
                    }
                    //update number of activities for which customer has registered
                    if (ticketsBought > 0) {
                        int numberRegisteredActivities = c.getRegisteredTickets();
                        numberRegisteredActivities++;
                        c.setRegisteredTickets(numberRegisteredActivities);
                        updateComplete = true;
                        //return updateComplete;
                    }
                }
            }
        } else if (tickets.size()==0) {
            System.out.println("ticketsize zero");
            for (int i = 0; i < activities.size(); i++) {
                if (ticketActivityName.compareTo(activities.get(i).getActivityName()) == 0) {
                    System.out.println("1: " + ticketActivityName);
                    Activity a = activities.get(i);
                    int ticketsAvailable = a.getTicketsAvailable();
                    ticketsAvailable -= ticketsBought;
                    System.out.println("2: " + ticketsAvailable);
                    a.setTicketsAvailable(ticketsAvailable);
                    System.out.println("Remaining tickets available for " + ticketActivityName + ": " + ticketsAvailable);
                    TicketOffice.getListOfTickets().add(t);
                }
            }
            //update number of activities for which customer has registered
            if (ticketsBought > 0) {
                int numberRegisteredActivities = c.getRegisteredTickets();
                numberRegisteredActivities++;
                c.setRegisteredTickets(numberRegisteredActivities);
                updateComplete = true;
                //return updateComplete;
            }
        }
        return updateComplete;
        }
    /*
    private static boolean update(Ticket t, int ticketsBought){
        boolean updateComplete = false;
        Customer c = t.getTicketCustomer();
        String ticketActivityName =t.getTicketActivityName();
        //update the available tickets for the activity:
        //this works for buying or returning
        for (int i = 0; i < activities.size(); i++) {
            if (ticketActivityName.compareTo(activities.get(i).getActivityName()) == 0) {
                Activity a = activities.get(i);
                int ticketsAvailable = a.getTicketsAvailable();
                ticketsAvailable -= ticketsBought;
                a.setTicketsAvailable(ticketsAvailable);
                System.out.println("Remaining tickets available for " + ticketActivityName + ": " + ticketsAvailable);
                TicketOffice.getListOfTickets().add(t);
            }
        }
        //update number of activities for which customer has registered
        if (ticketsBought > 0) {
            int numberRegisteredActivities = c.getRegisteredTickets();
            numberRegisteredActivities++;
            c.setRegisteredTickets(numberRegisteredActivities);
            updateComplete = true;
            return updateComplete;
            //for this, we want to know if the customer is returning all the tickets they bought for that activity
        } else if (ticketsBought < 0 && (-ticketsBought) == t.getTicketsBought()) {
            int numberRegisteredActivities = c.getRegisteredTickets();
            numberRegisteredActivities--;
            c.setRegisteredTickets(numberRegisteredActivities);
            System.out.println("You may register for an additional activity.");
            updateComplete = true;
            return updateComplete;
        } else if (ticketsBought < 0) {
            updateComplete = true;
            return updateComplete;
        }
        return updateComplete;
    }

     */
}
//example: a a buys 2 ticket for a. (8 tickets remaining) a a buys 4 ticket for a. (4 tickets remaining)
// a a returns 4 ticket for a. (8 tickets remaining) a a returns 4 ticket for a. (12 tickets remaining)
//now i bought two tickets (one for 2, one for 3) and it says that there's a total of six tickets possible to return
//TODO: FIGURE OUT why cancellations are so fucked up
//TODO: printing out customer is not showing the number registered activities they have
//this isn't working because it's modifying the active customer object instead of the one I want.
//try setting active customer equal to a a