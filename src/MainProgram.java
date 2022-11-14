import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class MainProgram {
    static final int MAX_NUMBER_ACTIVITIES_ALLOWED = 3;
    private static SortedArrayList<Activity> activities;
    private static SortedArrayList<Customer> customerList;
    private static ArrayList<PurchaseOrder> orders;

    public static void main(String[] args) {
        activities = new SortedArrayList<Activity>();
        readActivitiesFile();
        SortedArrayList.sortArrayList(activities);
        customerList = new SortedArrayList<Customer>();
        readCustomersFile();
        SortedArrayList.sortArrayList(customerList);
        orders = new ArrayList<PurchaseOrder>();
        boolean done = false;
        while (!done) {
            printMenu();
            try {
                Scanner userInput = new Scanner(System.in);
                char option = userInput.next().charAt(0);
                if (option == ('f') || option == ('a') || option == ('c') || option == ('t') || option == ('r') ||
                    option == ('F') || option == ('A') || option == ('C') || option == ('T') || option == ('R') ) {

                } else {
                    break;
                }
                //TODO: to make it only accept correct answer, make it .equals to the various options or else goodbye
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
                        for (Customer cust : customerList)
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
                                System.out.println("You have entered:");
                                System.out.println("First Name: " + firstName + ". Last Name: " + lastName);
                                if (checkName(firstName, lastName)) {
                                    nameCheck = true;
                                } else {
                                    System.out.println("This name is invalid. Please try again.");
                                    break;
                                }
                                int customerIndex = findCustomerIndex(firstName, lastName);
                                // System.out.println(customerIndex); // this is working
                                if (checkNumberActivities(customerList.get(customerIndex))) {
                                    numberActivitiesCheck = true;
                                } else if (!checkNumberActivities(customerList.get(customerIndex))) {
                                    System.out.println(customerList.get(customerIndex).getFirstName() + " " + customerList.get(customerIndex).getLastName() + " has registered for too many activities.");
                                    System.out.println("Please cancel an activity before adding a new one.");
                                    break;
                                }
                                System.out.println("Please enter the customer's chosen activity.");
                                String ticketActivityName = input.nextLine();
                                if (checkActivities(ticketActivityName)) {
                                    activityCheck = true;
                                } else if (!checkActivities(ticketActivityName)) {
                                    System.out.println(ticketActivityName + " is not a valid activity name. Please try again.");
                                    break;
                                }
                                System.out.println("Please enter the number of tickets to be bought.");
                                int ticketsBought = input.nextInt();
                                if (checkSufficientTickets(ticketActivityName, ticketsBought)) {
                                    ticketNumberCheck = true;
                                } else if (!checkSufficientTickets(ticketActivityName, ticketsBought)) {
                                    System.out.println("There are not enough tickets available. Please choose a different activity or quantity.");
                                    break;
                                }
                                if (nameCheck && numberActivitiesCheck && activityCheck && ticketNumberCheck) {
                                    boolean previouslyPurchased = false;
                                    PurchaseOrder activeOrder = new PurchaseOrder(customerList.get(customerIndex), ticketActivityName, ticketsBought);
                                    //if ticket is adding on to previously purchased order
                                    for (PurchaseOrder compareOrder : orders) {
                                        if (compareOrder.compareTo(activeOrder)==0){
                                            previouslyPurchased = true;
                                        } else { //else add the ticket
                                            previouslyPurchased = false;
                                            System.out.println("tix" + activeOrder);
                                            orders.add(activeOrder);
                                        }
                                    }
                                    if (orders.size() == 0 && !previouslyPurchased) {
                                        orders.add(activeOrder);
                                    }
                                    System.out.println("Dominic the donkey: " + PurchaseOrder.toString(activeOrder));
                                    boolean updateComplete = update(activeOrder, ticketsBought, previouslyPurchased);
                                    System.out.println(customerList.get(customerIndex).getFirstName() + " " + customerList.get(customerIndex).getLastName() + " has registered for " + customerList.get(customerIndex).getRegisteredOrders() + " activities.");
                                    tComplete = true;
                                    //checkSuccessful = true;
                                } else {
                                    System.out.println("Please try again. Thank you.");
                                    break;
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
                                //activeCustomer.setName(firstName, lastName);
                                System.out.println("You have entered:");
                                System.out.println("First Name: " + firstName + ". Last Name: " + lastName);
                                //check customer's name is valid
                                if (checkName(firstName, lastName)) {
                                    nameCheck = true;
                                } else {
                                    System.out.println("You have entered an invalid name. Please try again.");
                                    break;
                                }
                                //Customer tempC = new Customer(firstName, lastName, 0);
                                int customerIndex = findCustomerIndex(firstName, lastName);
                                //check activity name is valid.
                                System.out.println("Please enter the activity to be cancelled.");
                                String ticketActivityName = input.nextLine();
                                if (checkActivities(ticketActivityName)) {
                                    activityCheck = true;
                                } else if (!checkActivities(ticketActivityName)) {
                                    System.out.println(ticketActivityName + " is not a valid activity name. Please try again.");
                                    break;
                                }
                                //find ticket using customer name and activity name
                                //check the ticket has the same customer and activity names as input
                                int ticketIndex = 0;
                                for (int i = 0; i<orders.size(); i++) {
                                    int nameMatch = orders.get(i).getTicketCustomer().compareTo(customerList.get(customerIndex));
                                    if (nameMatch == 0) {
                                        int activityMatch = orders.get(i).getTicketActivityName().compareTo(ticketActivityName);
                                        if (activityMatch == 0) {
                                            ticketIndex = i;
                                        }
                                    }
                                }
                                //ask how many tickets to cancel
                                try {
                                    System.out.println("You have purchased " + orders.get(ticketIndex).getTicketsBought() + " tickets.");
                                    System.out.println("How many tickets will you cancel?");
                                    cancelQuantity = input.nextInt();
                                    if (cancelQuantity <= orders.get(ticketIndex).getTicketsBought()) {
                                        cancelQuantity = cancelQuantity * (-1);
                                        update(orders.get(ticketIndex), cancelQuantity, true);
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

    public static void readActivitiesFile() {
        String text;
        int numberCustomers;
        int numberActivities = 0;
        //SortedArrayList<Activity> activityArrayList;
        try {
            Scanner inFile = new Scanner(new FileReader("input.txt"));
            while (inFile.hasNextLine()) {
                numberActivities = parseInt(inFile.nextLine());
                for (int i = 0; i < numberActivities; i++) {
                    String activityName = inFile.nextLine();
                    int ticketsAvailable = parseInt(inFile.nextLine());
                    Activity activity1 = new Activity(activityName, ticketsAvailable);
                    activities.add(activity1);
                }
                //try removing this bit
                numberCustomers = parseInt(inFile.nextLine());
                for (int j = 0; j < numberCustomers; j++) {
                    text = inFile.nextLine();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, invalid file path.");
        }

    }

    public static void readCustomersFile() {
        int numberCustomers;
        int numberActivities = 0;
        //SortedArrayList<Customer> customerList;
        try {
            Scanner inFile = new Scanner(new FileReader("input.txt"));
            while (inFile.hasNextLine()) {
                numberActivities = parseInt(inFile.nextLine());
                for (int i = 0; i < numberActivities; i++) {
                    inFile.nextLine(); //to skip over activity name
                    inFile.nextLine(); //to skip over tickets
                }

                numberCustomers = parseInt(inFile.nextLine());
                for (int j = 0; j < numberCustomers; j++) {
                    String name = inFile.nextLine();
                    String separated[] = name.split(" ");
                    String firstName = separated[0];
                    String lastName = separated[1];
                    int registeredActivities=0;
                    Customer c = new Customer(firstName, lastName, registeredActivities);
                    customerList.add(c);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, invalid file path.");
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
    private static boolean checkName(String firstName, String lastName){
        Customer tempCustomer = new Customer(firstName, lastName, 0);
        boolean matchCustomer = false;
        //check customer match
        for (Customer cust : customerList) {
            if (cust.compareTo(tempCustomer) == 0) {
                matchCustomer = true;
                System.out.println(cust.getFirstName() + " " + cust.getLastName() + " is a valid name.");
            }
        }
        if (!matchCustomer) {
            System.out.println("This name does not belong to a registered customer. Please check the name and try again.");
        }
        return matchCustomer;
    }

    private static int findCustomerIndex(String first, String last) {
        Customer c = new Customer(first, last, 0);
        int j = 0;
        for (int i = 0; i<customerList.size(); i++){
            if (c.compareTo(customerList.get(i))==0){
                j=i;
            }
        }
        return j;
    }
    //check customer has registered for 3 or fewer activities
    private static boolean checkNumberActivities(Customer activeCustomer){
        int registeredActivities = activeCustomer.getRegisteredOrders();
        System.out.println(activeCustomer.getFirstName() + " " + activeCustomer.getLastName() + " has registered for " + registeredActivities + " activities.");
        boolean canRegister = false;
        if (activeCustomer.getRegisteredOrders()<MAX_NUMBER_ACTIVITIES_ALLOWED){
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
            } else if (ticketsBought <=availableTickets) {
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
    private static boolean update(PurchaseOrder t, int ticketsBought, boolean previouslyPurchased){
        System.out.println("previouslyPurchased " + previouslyPurchased);
        boolean updateComplete = false;
        //find correct customer in the customer array list
        int custIndex = 0;
        for (int i = 0; i<customerList.size(); i++){
            if (t.getTicketCustomer().compareTo(customerList.get(i))==0){
                custIndex=i;
            }
        }
        String ticketActivityName =t.getTicketActivityName();
        //find correct ticket in the ticket array list
        //TODO: THIS IS modifying a copy of the original, not the original itself
        //so the next loop can run even if there are no valid tickets.

        int tickIndex = 0;
        for (int i = 0; i<orders.size(); i++) {
            for (PurchaseOrder compareTicket : orders) {
                if (compareTicket.compareTo(t) == 0) {
                    tickIndex = i;
                }
            }
        }
        int previousTicketQuantity;
        for (PurchaseOrder compareOrder : orders) {
            if(previouslyPurchased) {
                //if customer has bought tickets for this activity before
                //update tickets on customer's purchase order to reflect more tickets bought
                previousTicketQuantity = orders.get(tickIndex).getTicketsBought();
                previousTicketQuantity += ticketsBought;
                orders.get(tickIndex).setTicketsBought(previousTicketQuantity);
                System.out.println("paddington roasts in ");
                //update available tickets in activity list
                for (int i = 0; i < activities.size(); i++) {
                    if (ticketActivityName.compareTo(activities.get(i).getActivityName()) == 0) {
                        System.out.println("3: " + ticketActivityName);
                        int ticketsAvailable = activities.get(i).getTicketsAvailable();
                        ticketsAvailable -= ticketsBought;
                        System.out.println("4: " + ticketsAvailable);
                        activities.get(i).setTicketsAvailable(ticketsAvailable);
                        System.out.println("Remaining tickets available for " + ticketActivityName + ": " + ticketsAvailable);
                        updateComplete = true;
                        //return updateComplete;
                    }
                }
            } else if (!previouslyPurchased) { //if this is a new activity for the customer
                for (int i = 0; i < activities.size(); i++) {
                    if (ticketActivityName.compareTo(activities.get(i).getActivityName()) == 0) {
                        System.out.println("1: " + ticketActivityName);
                        int ticketsAvailable = activities.get(i).getTicketsAvailable();
                        ticketsAvailable -= ticketsBought;
                        System.out.println("2: " + ticketsAvailable);
                        activities.get(i).setTicketsAvailable(ticketsAvailable);
                        System.out.println("Remaining tickets available for " + ticketActivityName + ": " + ticketsAvailable);
                        //tickets.add(t); now added in main
                    }
                }
                //update number of activities for which customer has registered
                if (ticketsBought > 0) {
                    int numberRegisteredActivities = customerList.get(custIndex).getRegisteredOrders();
                    numberRegisteredActivities++;
                    customerList.get(custIndex).setRegisteredOrders(numberRegisteredActivities);
                    System.out.println("updating mcupdate");
                    updateComplete = true;
                    //return updateComplete;
                }
            }
        }
        t.setTicketCustomer(customerList.get(custIndex));
        return updateComplete;
    }
    }
//now i bought two tickets (one for 2, one for 3) and it says that there's a total of six tickets possible to return
//TODO: FIGURE OUT why cancellations are so fucked up
//TODO: IF customer reduces tickets to zero, remove the order using orders.remove()
//TODO: CHANGE class Ticket to PurchasedOrders