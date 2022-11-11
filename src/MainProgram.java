import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class MainProgram {
    static final int MAX_NUMBER_ACTIVITIES_ALLOWED = 3;
    private static SortedArrayList<Activity> activities;
    private static SortedArrayList<Customer> customers;
    //private static ArrayList<Ticket> tickets;

    public static void main(String[] args) {
        TicketOffice.readActivitiesFile();
        activities = TicketOffice.getActivityArrayList();
        activities.sortArrayList(activities);
        TicketOffice.readCustomersFile();
        customers = TicketOffice.getCustomerList();
        customers.sortArrayList(customers);
        Customer c = new Customer("Alpha", "Bet", 0);
        customers.add(c);
        //System.out.println(c);

        boolean done = false;
        Customer activeCustomer = new Customer();
        String ticketActivityName = new String();
        int ticketQuantity = 0;
        Ticket activeTicket = new Ticket(activeCustomer, ticketActivityName, ticketQuantity);
        while (!done) {
            //Customer activeCustomer = new Customer();
            //String ticketActivityName = new String();
            int ticketsBought = 0;
            //Ticket activeTicket = new Ticket();
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
                        boolean tComplete = false;
                        while (!tComplete) {
                            boolean nameCheck = false;
                            boolean numberActivitiesCheck = false;
                            boolean activityCheck = false;
                            boolean ticketNumberCheck = false;
                            boolean checkSuccessful = false;
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
                                    //store ticket in ticket office
                                    TicketOffice.getListOfTickets().add(activeTicket);
                                    for (Ticket t : TicketOffice.getListOfTickets()) {
                                        System.out.println(Ticket.toString(t));
                                    }
                                    checkSuccessful = true;
                                } else if (!checkSuccessful) {
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
                            System.out.println("at end of main in t:");
                            activeTicket.setTicketCustomer(activeCustomer);
                            System.out.println("ticketActivityName " + ticketActivityName);
                            activeTicket.setTicketActivityName(ticketActivityName);
                            System.out.println("ticketsBought " + ticketsBought);
                            activeTicket.setTicketsBought(ticketsBought);
                            System.out.println("Active ticket in main");
                            System.out.println(Ticket.toString(activeTicket));

                            boolean updateComplete = update(activeTicket, ticketsBought);
                            System.out.println("update complete? " + updateComplete);
                            System.out.println(activeCustomer.getFirstName() + " " + activeCustomer.getLastName() + " has registered for " + activeCustomer.getRegisteredTickets() + " activities.");
                            System.out.println("Check to see this customer has a registered activity");
                            System.out.println(activeCustomer.toString());
                            tComplete = true;
                        break;
                    case "r": //Update info after ticket canceled.
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
                                    if (cancelQuantity == activeTicket.getTicketsBought()) {
                                        update(activeTicket, cancelQuantity);
                                        //reduce number of registered activities
                                    } else if (cancelQuantity < activeTicket.getTicketsBought()) {
                                        update(activeTicket, cancelQuantity);
                                    } else {
                                        System.out.println("You have requested a cancellation for more tickets than are available.");
                                        System.out.println("Please affirm the quantity and try again.");
                                        break; //note, may change this section to a while loop. have boolean set at top and this way clerk doesn't have to runthrough all the toptions again
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
            print(ticketActivityName, ticketsBought, availableTickets);
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
    private static void print(String ticketActivityName, int ticketsBought, int availableTickets){
        PrintWriter outFile;
            try {
                outFile = new PrintWriter("letters.txt");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        outFile.write("A customer attempted to buy " + ticketsBought + " for " + ticketActivityName + " but only " + availableTickets + " are available.");
        outFile.close();
    }
    private static boolean update(Ticket t, int ticketsBought){
        boolean updateComplete = false;
        Customer c = t.getTicketCustomer();
        String ticketActivityName =t.getTicketActivityName();
        System.out.println("in update method");
        System.out.println(Ticket.toString(t));

        //update the available tickets for the activity:
        //this works for buying or returning
        for (int i = 0; i < activities.size(); i++) {
            if (ticketActivityName.compareTo(activities.get(i).getActivityName()) == 0) {
                Activity a = activities.get(i);
                int ticketsAvailable = a.getTicketsAvailable();
                ticketsAvailable -= ticketsBought;
                a.setTicketsAvailable(ticketsAvailable);
                System.out.println("Remaining available tickets: " + ticketsAvailable);
                //updateComplete=true;
            }
        }
        //update number of activities for which customer has registered
        if (ticketsBought > 0) {
            int numberRegisteredActivities = c.getRegisteredTickets();
            numberRegisteredActivities++;
            c.setRegisteredTickets(numberRegisteredActivities);
            updateComplete = true;
            return updateComplete;
            //for this, we want to know if the customer is returning all of the tickets they bought for that activity
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
    /* i want to return the ticket but only the good one, so I am moving this to main
    private static Ticket checkTicketForCancellation(Customer c, String ticketActivityName) {
        //boolean ticketValid = false;
        ArrayList<Ticket> tickets = TicketOffice.getListOfTickets();
        for (Ticket t : tickets) {
            int nameMatch = t.getTicketCustomer().compareTo(c);
            if (nameMatch == 0) {
                int activityMatch = t.getTicketActivityName().compareTo(ticketActivityName);
                if (activityMatch == 0) {
                    return t;
                }
            }
        }
        //return ticketValid;
    }

     */
}
//TODO: method checkTicketQuantity should write to the letters text file
//TODO: if someone mistypes a name, they still have to enter the activity and #tickets before returning to the main menu.
//TODO: check the update of the tickets for the user vs tickets available for activity
//TODO: consider if a customer wants to purchase additional tickets for an activity for which they are already registered. current code adds on an additional registered activity.
//TODO: might consider rolling back to going through ticket array list and looking for the customer name on the ticket. if so, must add a compare to for the tickets