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
        //SortedArrayList.sortArrayList(activities);
        customerList = new SortedArrayList<Customer>();
        readCustomersFile();
        //SortedArrayList.sortArrayList(customerList);
        orders = new ArrayList<PurchaseOrder>();
        boolean done = false;
        while (!done) {
            printMenu();
            try {
                char option = 0;
                Scanner userInput = new Scanner(System.in);
                String entry = userInput.next();
                if (entry.equals("f") || entry.equals("a") || entry.equals("c") || entry.equals("t") || entry.equals("r") ||
                        entry.equals("F") || entry.equals("A") || entry.equals("C") || entry.equals("T") || entry.equals("R") ) {
                    option = entry.charAt(0);
                }

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
                        Scanner input = new Scanner(System.in);
                        boolean tComplete = false;
                        while (!tComplete) {
                            //TODO: try eliminating tcomplete
                            try {
                                //the following method takes in clerk input and uses it to find the customer in the list. If the customer can't be found, exit the loop.
                                int customerIndex = findCustomer();
                                if (customerIndex < 0) {
                                    System.out.println("Please check the customer's name and try again.");
                                    break;
                                }
                                if (!checkNumberActivities(customerList.get(customerIndex))) {
                                    System.out.println(customerList.get(customerIndex).getFirstName() + " " + customerList.get(customerIndex).getLastName() + " has registered for too many activities.");
                                    System.out.println("Please cancel an activity before adding a new one.");
                                    break;
                                }
                                String ticketActivityName = findActivityName();
                                if (ticketActivityName == null) {
                                    break;
                                }
                                System.out.println("Please enter the number of tickets to be bought.");
                                int ticketsBought = input.nextInt();
                                if (ticketsBought<=0) {
                                    break;
                                }
                                if (!checkSufficientTickets(ticketActivityName, ticketsBought, customerList.get(customerIndex).getFirstName(), customerList.get(customerIndex).getLastName())) {
                                    System.out.println("There are not enough tickets available. Please choose a different activity or quantity.");
                                    break;
                                }
                                boolean previouslyPurchased = checkIfPreviouslyPurchased(customerIndex, ticketActivityName, ticketsBought);
                                PurchaseOrder activeOrder = new PurchaseOrder(customerList.get(customerIndex), ticketActivityName, ticketsBought);
                                if (orders.size() == 0 && !previouslyPurchased) {
                                    orders.add(activeOrder);
                                } else if (!previouslyPurchased) {
                                    orders.add(activeOrder);
                                }
                                update(activeOrder, ticketsBought, previouslyPurchased); //TODO: update is too busy. break up into placeorder and cancelorder methods
                                System.out.println(customerList.get(customerIndex).getFirstName() + " " + customerList.get(customerIndex).getLastName() + " has registered for " + customerList.get(customerIndex).getRegisteredOrders() + " activities.");
                                tComplete = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Please enter a valid option.");
                            } //TODO: is index out of bounds necessary now that the name input is in a different method?
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
                                //the following method takes in clerk input and uses it to find the customer in the list. If the customer can't be found, exit the loop.
                                int customerIndex = findCustomer();
                                if (customerIndex == -1) {
                                    System.out.println("Please check the customer's name and try again.");
                                    break;
                                }
                                //check activity name is valid.
                                String ticketActivityName = findActivityName();
                                if (ticketActivityName == null) {
                                    break;
                                }
                                //find ticket using customer name and activity name
                                //check the ticket has the same customer and activity names as input
                                //TODO: CONSIDER if a customer requests a return when there are no orders placed yet.
                                int poIndex = getPOIndex(customerList.get(customerIndex), ticketActivityName, 0);
                                //ask how many tickets to cancel
                                int cancelQuantity = getCancelQuantity(poIndex);
                                if (cancelQuantity <=0) {
                                    break;
                                }
                                if (cancelQuantity <= orders.get(poIndex).getTicketsBought()) {
                                    cancelQuantity = cancelQuantity * (-1);
                                    update(orders.get(poIndex), cancelQuantity, true);
                                    //TODO: is this a TODO? it wasn't marked that way so check on this: reduce number of registered activities
                                } else {
                                    System.out.println("You have requested a cancellation for more tickets than are available.");
                                    System.out.println("Please affirm the quantity and try again.");
                                    break;
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

    /**
     * This method reads the input file. The first line is the number of activities.
     * A for loop is run for as many iterations as there are activities.
     * The ticket activity name and number of tickets available are read in, used to construct an Activity object, and that Activity object is added to the arraylist.
     * The customers portion of the input file is ignored in this method.
     */
    public static void readActivitiesFile() {
        int numberCustomers;
        int numberActivities = 0;
        try {
            Scanner inFile = new Scanner(new FileReader("input.txt"));
            while (inFile.hasNextLine()) {
                numberActivities = parseInt(inFile.nextLine());
                for (int i = 0; i < numberActivities; i++) {
                    String activityName = inFile.nextLine();
                    int ticketsAvailable = parseInt(inFile.nextLine());
                    Activity activity1 = new Activity(activityName, ticketsAvailable);
                    SortedArrayList.addElement(activities, activity1);
                    //activities.add(activity1);
                }
                numberCustomers = parseInt(inFile.nextLine());
                for (int j = 0; j < numberCustomers; j++) {
                    String text = inFile.nextLine();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, invalid file path.");
        }

    }

    /**
     * This method reads the input file. The first line is the number of activities.
     * A for loop is run for as many iterations as there are activities and skips over these lines.
     * After the activities section, the number of customers is given.
     * A for loop is run for as many iterations as there are customers.
     * The name is read in and split between first and last name.
     * The customer is added to the customer list.
     */
    public static void readCustomersFile() {
        int numberCustomers;
        int numberActivities = 0;
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
                    SortedArrayList.addElement(customerList, c);
                    //customerList.add(c);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, invalid file path.");
        }
    }

    /**
     * This is a menu that explains the options to the clerk.
     */

    private static void printMenu() {
        System.out.println("Welcome to the Samsung Resort on Geoje.");
        System.out.println("Please choose from one of the following options.");
        System.out.println("f: Exit the program.");
        System.out.println("a: Display information about all activities.");
        System.out.println("c: Display information about all registered customers.");
        System.out.println("t: Update stored data after customer buys a ticket.");
        System.out.println("r: Update stored data after customer cancels a ticket.");
    }

    private static int findCustomer(){
        int customerIndex = -1;
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
                customerIndex = findCustomerIndex(firstName, lastName);
            } else {
                customerIndex = -1;
            }

        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid option.");
        }
        catch (IndexOutOfBoundsException f) {
            System.out.println("Please check your input and try again. Thank you.");
        }
        return customerIndex;
    }

    private static String findActivityName(){
        String ticketActivityName = null;
        try {
            System.out.println("Please enter the customer's chosen activity.");
            Scanner input = new Scanner(System.in);
            ticketActivityName = input.nextLine();
            if (!checkActivities(ticketActivityName)) {
                System.out.println(ticketActivityName + " is not a valid activity name. Please try again.");
                ticketActivityName = null;
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid option.");
        }
        return ticketActivityName;
    }

    /**
     * This method builds a temporary customer using the parameters first name and last name.
     * It compares the temporary customer name to the list of registered customers and returns a boolean value for if a match is found.
     * @param firstName: this parameter is input by clerk.
     * @param lastName: this parameter is input by clerk.
     * @return boolean value for finding a match for the customer. These boolean values are used frequently to allow the clerk to break out of the entry portion in case of a typo.
     */
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

    /**
     * This method builds a temporary customer using the parameters first name and last name.
     * It compares the temporary customer name to the list of registered customers.
     * Once found, it returns the index at which the customer is located.
     * @param first: first name as verified in method checkName()
     * @param last: last name as verified in method checkName()
     * @return index of customer in arraylist customerList.
     */
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

    /**
     * Customers are only allowed to register for 3 activities at a time.
     * The number of registered activities is saved in the Customer object.
     * This method compares the number of registered activities customer is enrolled in to the maximum number of activities allowed.
     * It returns a boolean as to whether the customer may register.
     * @param activeCustomer the customer parameter is used to find the number of registered activities for which the customer is enrolled.
     * @return boolean value if customer has registered for too many activities.
     */
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

    /**
     * This method checks the clerk input for activity name against the arraylist of activity objects
     * @param ticketActivityName: this is clerk input for activity name
     * @return boolean if match is found
     */
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

    /**
     * This method checks if the number of tickets the customer requests are available.
     * @param ticketActivityName: this parameter is used to find the activity object in the activity arraylist; it is used to find the number of available tickets from that Activity object.
     * @param ticketsBought
     * @param firstName
     * @param lastName
     * @return
     */
    private static boolean checkSufficientTickets(String ticketActivityName, int ticketsBought, String firstName, String lastName) {
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
                print(ticketActivityName, ticketsBought, availableTickets, firstName, lastName);
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

    /**
     * This method builds a purchase order to use as a search term in order list.
     * In so doing, we can see if the customer has previously purchased tickets for this activity.
     * If so, the number of activities for which they are registered will not increase.
     * If not, this is a new activity for them and they will increment number of registered activities.
     * @param customerIndex: finds customer using parameter customer index
     * @param ticketActivityName: name of activity
     * @param ticketsBought: number of tickets the customer wishes to purchase
     * @return boolean to be used to determine if the number of POs should be incremented.
     */
    private static boolean checkIfPreviouslyPurchased(int customerIndex, String ticketActivityName, int ticketsBought) {
        boolean previouslyPurchased = false;
        PurchaseOrder activeOrder = new PurchaseOrder(customerList.get(customerIndex), ticketActivityName, ticketsBought);
        //if ticket is adding on to previously purchased order
        for (PurchaseOrder compareOrder : orders) {
            if (compareOrder.compareTo(activeOrder) == 0) {
                previouslyPurchased = true;
            } else {
                previouslyPurchased = false;
            }
        }
        return previouslyPurchased;
    }

    /**
     * In the case that the customer has requested more tickets for an activity than are available, a letter is issued.
     * @param ticketActivityName the ticket activity name is used in the letter
     * @param ticketsBought number of tickets bought is used in the letter
     * @param availableTickets number of available tickets is used in the letter
     * @param firstName customer's first name is used in the letter
     * @param lastName customer's last name is used in the letter
     */
    private static void print(String ticketActivityName, int ticketsBought, int availableTickets, String firstName, String lastName){
        PrintWriter outFile;
        try {
            outFile = new PrintWriter("letters.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        outFile.write("Dear " + firstName + " " + lastName + "," + "\n");
        outFile.write("Thank you for your interest in " + ticketActivityName + ". Unfortunately, there are only " + availableTickets + " tickets available for " + ticketActivityName + " at present." +"\n");
        outFile.write("We invite you to try another of our activities." +"\n");
        outFile.write("Sincerely," +"\n");
        outFile.write("The Samsung Hotel at Geoje.");
        outFile.close();
    }

    /**
     * the update method uses submethods to update the number of available tickets for the activity in question and, if necessary, the number of registered activities for a customer
     * @param po the purchase order is used to find the customer index and the poIndex.
     * @param ticketsBought number of tickets customer is buying or returning. if the number is positive, customer is buying. if negative, customer is returning.
     * @param previouslyPurchased if customer has previously purchased tickets for this activity, the existing purchase order is updated.
     */
    private static void update(PurchaseOrder po, int ticketsBought, boolean previouslyPurchased){
        //find correct customer in the customer array list
        int custIndex = findCustomerIndex(po.getTicketCustomer().getFirstName(), po.getTicketCustomer().getLastName());
        //find correct purchase order in the purchase order array list
        int poIndex = getPOIndex(po.getTicketCustomer(), po.getTicketActivityName(), po.getTicketsBought());
        //if customer is returning all the tickets purchased for an activity
        if (previouslyPurchased && (-1 * ticketsBought) == po.getTicketsBought()) {
            updateAvailableTickets(poIndex, ticketsBought);
            decrementNumberPOs(custIndex);
            orders.remove(poIndex);
        } else if(previouslyPurchased) {
            updatePOTickets(poIndex, ticketsBought);
            //update available tickets in activity list
            updateAvailableTickets(poIndex, ticketsBought);
        } else if (!previouslyPurchased) { //if this is a new activity for the customer
            updateAvailableTickets(poIndex, ticketsBought);
            //update number of activities for which customer has registered
            if (ticketsBought > 0) {
                incrementNumberPOs(custIndex);
            }
        }
    }

    /**
     * find the purchase order in the purchase order array list.
     * @param c
     * @param ticketActivityName
     * @param ticketsBought
     * @return
     */
    private static int getPOIndex(Customer c, String ticketActivityName, int ticketsBought) {
        PurchaseOrder po = new PurchaseOrder(c, ticketActivityName, ticketsBought);
        int poIndex = 0;
        for (int i = 0; i<orders.size(); i++) {
            for (PurchaseOrder compareOrder : orders) {
                if (compareOrder.compareTo(po) == 0) {
                    poIndex = i;
                }
            }
        }
        return poIndex;
    }

    /**
     *
     * @param poIndex
     * @param ticketsBought
     */
    private static void updatePOTickets(int poIndex, int ticketsBought){
        //update tickets on customer's purchase order to reflect more tickets bought
        int previousTicketQuantity = orders.get(poIndex).getTicketsBought();
        previousTicketQuantity += ticketsBought;
        orders.get(poIndex).setTicketsBought(previousTicketQuantity);
    }

    private static void updateAvailableTickets(int poIndex, int ticketsBought){
        String ticketActivityName = orders.get(poIndex).getTicketActivityName();
        for (int i = 0; i < activities.size(); i++) {
            if (ticketActivityName.compareTo(activities.get(i).getActivityName()) == 0) {
                int ticketsAvailable = activities.get(i).getTicketsAvailable();
                ticketsAvailable -= ticketsBought;
                activities.get(i).setTicketsAvailable(ticketsAvailable);

            }
        }
    }

    private static int getCancelQuantity(int poIndex) {
        int cancelQuantity = 0;
        try {
            System.out.println("You have purchased " + orders.get(poIndex).getTicketsBought() + " tickets.");
            System.out.println("How many tickets will you cancel?");
            Scanner input = new Scanner(System.in);
            cancelQuantity = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Please check your input and try again.");
        }
        catch (IndexOutOfBoundsException f) {
        System.out.println("Please check your input and try again. Thank you.");
    }
    return cancelQuantity;
    }

    private static void incrementNumberPOs(int custIndex){
        int numberRegisteredActivities = customerList.get(custIndex).getRegisteredOrders();
        numberRegisteredActivities++;
        customerList.get(custIndex).setRegisteredOrders(numberRegisteredActivities);
    }

    private static void decrementNumberPOs(int custIndex){
        int numberRegisteredActivities = customerList.get(custIndex).getRegisteredOrders();
        numberRegisteredActivities--;
        customerList.get(custIndex).setRegisteredOrders(numberRegisteredActivities);
    }
}

//TODO: separate out the update into incrementing and decrementing. It's confusing how it says choose how many tickets to be bought instead of returned, and when they try to return too many it says not enough tickets available.
//TODO: if customer purchases tickets for activity a but selects b when canceling, program doesn't know it's wrong.
