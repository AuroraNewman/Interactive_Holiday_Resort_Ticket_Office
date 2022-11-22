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
        customerList = new SortedArrayList<Customer>();
        readCustomersFile();
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
                        //the following method takes in clerk input and uses it to find the customer in the list. If the customer can't be found, exit the loop.
                        int customerIndex = inputCustomerName();
                        if (customerIndex < 0) {
                            System.out.println("Please check the customer's name and try again.");
                            break;
                        }
                        //the following method takes in clerk input and checks it against the list of activities. If the activity can't be found, exit the loop.
                        int activityIndex = inputActivityName();
                        if (activityIndex < 0) {
                            break;
                        }
                        //enter the number of tickets to be bought. break loop if the number is invalid
                        int ticketQuantity = enterTicketQuantity(activityIndex);
                        if (ticketQuantity<=0) {
                            System.out.println("Please enter a positive integer.");
                            break;
                        }
                        //check the number of tickets the customer wants are available
                        if (!checkSufficientTickets(activityIndex, ticketQuantity, customerList.get(customerIndex).getFirstName(), customerList.get(customerIndex).getLastName())) {
                            break;
                        }
                        //check if customer has previously registered for this activity
                        boolean previouslyPurchased = checkIfPreviouslyPurchased(customerIndex, activityIndex, ticketQuantity);
                        //the following method checks number of registered activities for the customer.
                        //If the customer has 3 activities already and is trying to add a new activity, exit the loop.
                        boolean canRegister = checkNumberActivities(customerList.get(customerIndex));
                        if (!canRegister && !previouslyPurchased) {
                            System.out.println(customerList.get(customerIndex).getFirstName() + " " + customerList.get(customerIndex).getLastName() + " has registered for too many activities.");
                            System.out.println("Please cancel an activity before adding a new one.");
                            break;
                        }
                        PurchaseOrder activeOrder = new PurchaseOrder(customerList.get(customerIndex), activities.get(activityIndex).getActivityName(), ticketQuantity);
                        if (orders.size() == 0 && !previouslyPurchased) {
                            orders.add(activeOrder);
                        } else if (!previouslyPurchased) {
                            orders.add(activeOrder);
                        }
                        updateAdd(activeOrder, ticketQuantity, previouslyPurchased, activityIndex);
                        if (customerList.get(customerIndex).getRegisteredOrders() == 1) {
                            System.out.println(customerList.get(customerIndex).getFirstName() + " " + customerList.get(customerIndex).getLastName() + " has registered for " + customerList.get(customerIndex).getRegisteredOrders() + " activity.");
                        } else {
                            System.out.println(customerList.get(customerIndex).getFirstName() + " " + customerList.get(customerIndex).getLastName() + " has registered for " + customerList.get(customerIndex).getRegisteredOrders() + " activities.");
                        }
                        break;
                    case 'r': //Update info after ticket canceled.
                    case 'R':
                        //the following method takes in clerk input and uses it to find the customer in the list. If the customer can't be found, exit the loop.
                        customerIndex = inputCustomerName();
                        if (customerIndex == -1) {
                            System.out.println("Please check the customer's name and try again.");
                            break;
                        }
                        //the following method takes in clerk input and checks it against the list of activities. If the activity can't be found, exit the loop.
                        activityIndex = inputActivityName();
                        if (activityIndex == -1) {
                            break;
                        }
                        //find purchase order using customer name and activity name
                        //check the purchase order has the same customer and activity names as input
                        int poIndex = getPOIndex(customerList.get(customerIndex), activities.get(activityIndex).getActivityName(), 0);
                        if (poIndex == -1) {
                            System.out.println(customerList.get(customerIndex).getFirstName() + " " + customerList.get(customerIndex).getLastName() + " has not previously bought tickets for " + activities.get(activityIndex).getActivityName() + ".");
                            System.out.println("Please check your input and try again.");
                        }
                        //ask how many tickets to cancel. break loop if the number is invalid
                        int cancelQuantity = getCancelQuantity(poIndex);
                        if (cancelQuantity <=0) {
                            break;
                        }
                        //check customer has purchased as many tickets as they are requesting to be cancelled
                        if (cancelQuantity <= orders.get(poIndex).getTicketsBought()) {
                            cancelQuantity = cancelQuantity * (-1);
                            updateCancel(customerIndex, poIndex, cancelQuantity, activityIndex);
                        } else {
                            System.out.println("You have requested a cancellation for more tickets than purchased.");
                            System.out.println("Please affirm the quantity and try again.");
                            break;
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
                    activities.add(activity1);
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
                    customerList.add(c);
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

    /**
     * Accepts clerk input for name.
     * Checks input against the list of customers
     * @return the index at which the customer is located.
     */
    private static int inputCustomerName(){
        int customerIndex = -1;
        try {
            System.out.println("Please enter the customer's full name, first and last.");
            Scanner input = new Scanner(System.in);
            String name = input.nextLine();
            String separated[] = name.split(" ");
            String firstName = separated[0];
            String lastName = separated[1];
            //System.out.println("You have entered:");
            //System.out.println("First Name: " + firstName + ". Last Name: " + lastName);
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

    /**
     * This method builds a temporary customer using the parameters first name and last name.
     * It compares the temporary customer name to the list of registered customers and returns a boolean value for if a match is found.
     * @param firstName: customer first name for comparison
     * @param lastName: customer last name for comparison
     * @return boolean value for finding a match for the customer.
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
            System.out.println(firstName + " " + lastName + " is not a registered customer.");
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
     * Accepts clerk input for activity name.
     * Checks input against the list of activities using a secondary method.
     * @return index at which the activity may be located in the list of activities
     */
    private static int inputActivityName(){
        String ticketActivityName = null;
        int activityIndex = -1;
        try {
            System.out.println("Please enter the customer's chosen activity.");
            Scanner input = new Scanner(System.in);
            ticketActivityName = input.nextLine();
            activityIndex = checkActivities(ticketActivityName);
            if (activityIndex == -1) {
                System.out.println(ticketActivityName + " is not a valid activity name. Please try again.");
                ticketActivityName = null;
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid activity name.");
        }
        return activityIndex;
    }

    /**
     * Customers are only allowed to register for 3 activities at a time.
     * The number of registered activities is saved in the Customer object.
     * This method compares the number of registered activities customer is enrolled in to the maximum number of activities allowed.
     * It returns a boolean whether the customer may register.
     * @param activeCustomer the customer parameter is used to find the number of registered activities for which the customer is enrolled.
     * @return boolean value if customer has registered for too many activities.
     */
    private static boolean checkNumberActivities(Customer activeCustomer){
        boolean canRegister = false;
        if (activeCustomer.getRegisteredOrders()<MAX_NUMBER_ACTIVITIES_ALLOWED){
            canRegister = true;
        }
        return canRegister;
    }

    /**
     * This method checks activity name against the arraylist of activity objects and finds the index where the activity is located
     * @param ticketActivityName: activity name to be checked
     * @return index where the activity is located
     */
    private static int checkActivities(String ticketActivityName){
        int activityIndex = -1;
        for (int i = 0; i < activities.size(); i++) {
            if (ticketActivityName.equalsIgnoreCase((activities.get(i).getActivityName()))) {
                System.out.println("You have chosen: " + ticketActivityName + ".");
                activityIndex = i;
            }
        }
        return activityIndex;
    }

    /**
     * accepts clerk input for number of tickets to purchase for a given activity found at the activity index
     * @param activityIndex used to find the activity in the activities arraylist
     * @return the number of tickets customer wants to purchase
     */
    private static int enterTicketQuantity(int activityIndex) {
        int ticketQuantity = -1;
        System.out.println("There are " + activities.get(activityIndex).getTicketsAvailable() + " tickets available for " + activities.get(activityIndex).getActivityName() + ".");
        System.out.println("How many tickets would you like to purchase?");
        try {
            Scanner input = new Scanner(System.in);
            ticketQuantity = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Please enter a positive integer using digits instead of letters (e.g., 1 and not one.)");
        }
        return ticketQuantity;
    }
    /**
     * checks if there are sufficient available tickets to accommodate customer request
     * uses a secondary method print if there are insufficient tickets available
     * @param activityIndex index to find the activity
     * @param ticketsBought number of tickets customer is requesting
     * @param firstName customer's first name
     * @param lastName customer's last name
     * @return boolean match if there are sufficient tickets
     */
    private static boolean checkSufficientTickets(int activityIndex, int ticketsBought, String firstName, String lastName) {
        int availableTickets = activities.get(activityIndex).getTicketsAvailable();
        String ticketActivityName = activities.get(activityIndex).getActivityName();
        boolean ticketsAvailable = false;
            if (ticketsBought > availableTickets) {
                System.out.println("You requested " + ticketsBought + " tickets for " + ticketActivityName + ", but only " + availableTickets + " tickets are available.");
                print(ticketActivityName, ticketsBought, availableTickets, firstName, lastName);
            } else if (ticketsBought <=availableTickets) {
                ticketsAvailable = true;
            } else {
                System.out.println("Please enter a valid number of tickets. Thank you.");
            }
        //}
        return ticketsAvailable;
    }
    /**
     * In the case that the customer has requested more tickets for an activity than are available, a letter is issued.
     * if the customer requests a number of tickets that could be accommodated by a different activity, those activities are suggested
     * @param ticketActivityName the ticket activity name is used in the letter
     * @param ticketsBought number of tickets bought is used in the letter
     * @param availableTickets number of available tickets is used in the letter
     * @param firstName customer's first name is used in the letter
     * @param lastName customer's last name is used in the letter
     */
    private static void print(String ticketActivityName, int ticketsBought, int availableTickets, String firstName, String lastName){
        ArrayList<String> availableActivities = null;
        PrintWriter outFile;
        try {
            outFile = new PrintWriter("letters.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        boolean availableActivityExists = areActivitiesAvailable(ticketsBought);
        if (availableActivityExists) {
            availableActivities = findAvailableActivities(ticketsBought);
        }
        outFile.write("Dear " + firstName + " " + lastName + "," + "\n");
        outFile.write("Thank you for your interest in " + ticketActivityName + ". Unfortunately, there are only " + availableTickets + " tickets available for " + ticketActivityName + " at present." +"\n");
        outFile.write("We invite you to try another of our activities." +"\n");
        if (availableActivityExists) {
            outFile.write("The following activities have more than " + ticketsBought + " tickets available: " + availableActivities.toString() +"\n");
        }
        outFile.write("Sincerely," +"\n");
        outFile.write("The Samsung Hotel at Geoje.");
        outFile.close();
    }

    /**
     * this method is used in the print() method.
     * in this case, the customer has requested more tickets than are available for their desired activity.
     * this method looks through the activity list to see if there are any activities that can accommodate their group size
     * @param ticketsBought number of tickets customer attempted to buy
     * @return true if the group can be accommodated at a different activity and false if they cannot
     */
    private static boolean areActivitiesAvailable(int ticketsBought) {
        boolean availableActivityExists = false;
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).getTicketsAvailable() >= ticketsBought) {
                availableActivityExists = true;
            }
        }
        return availableActivityExists;
    }

    /**
     * this method is used in the print() method.
     * in this case, the customer has requested more tickets than are available for their desired activity.
     * this method is after using the areActivitiesAvailable() method to find if there are any available activities to accommodate the group size
     * this method loops through all the activities, pulls out the activity name, and adds it to an arraylist
     * @param ticketsBought number of tickets customer attempted to buy
     * @return arraylist of activities that can accommodate the group.
     */
    private static ArrayList<String> findAvailableActivities(int ticketsBought) {
        ArrayList<String> availableActivities = new ArrayList<>();
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).getTicketsAvailable() >= ticketsBought) {
                availableActivities.add(activities.get(i).getActivityName());
            }
        }
        return availableActivities;
    }
    /**
     * check if customer is adding on to a previous purchase order or placing a new order
     * use the parameters to construct a temporary order by which every other purchase order is compared
     * @param customerIndex index at which customer is located in arraylist customerList
     * @param activityIndex index at which activity is located in arraylist activities
     * @param ticketsBought number of tickets customer is purchasing
     * @return if a match is found, true is returned. if not, false is returned.
     */
    private static boolean checkIfPreviouslyPurchased(int customerIndex, int activityIndex, int ticketsBought) {
        boolean previouslyPurchased = false;
        PurchaseOrder activeOrder = new PurchaseOrder(customerList.get(customerIndex), activities.get(activityIndex).getActivityName(), ticketsBought);
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
     * this method is used to update after a purchase
     * the customer is first found in the array list
     * the purchase order index is next found
     * the checkIfPreviouslyPurchased() method is used to find if the customer is adding more tickets to a previously placed order or making a new order
     * if it is adding on to a previous order, the number of tickets on the purchase order is updated using updatePOTickets() method and the number of
     *  available tickets are reduced using the updateAvailableTickets() method.
     * it is a new order, the available tickets are reduced using the updateAvailableTickets() method and the number of registered activities are
     *  incremented using the incrementNumberPOs() method
     * @param po purchase order being placed
     * @param ticketsBought number tickets being bought
     * @param previouslyPurchased if this is a new order or adding on to a previously purchased order
     */
    private static void updateAdd(PurchaseOrder po, int ticketsBought, boolean previouslyPurchased, int activityIndex){
        //find correct customer in the customer array list
        int custIndex = findCustomerIndex(po.getTicketCustomer().getFirstName(), po.getTicketCustomer().getLastName());
        //find correct purchase order in the purchase order array list
        int poIndex = getPOIndex(po.getTicketCustomer(), po.getTicketActivityName(), po.getTicketsBought());
        if(previouslyPurchased) {
            //update ticket quantity on the purchase order
            updatePOTickets(poIndex, ticketsBought);
            //update available tickets in activity list
            updateAvailableTickets(ticketsBought, activityIndex);
        } else if (!previouslyPurchased) { //if this is a new activity for the customer
            updateAvailableTickets(ticketsBought, activityIndex);
            //update number of activities for which customer has registered
            if (ticketsBought > 0) {
                incrementNumberPOs(custIndex);
            }
        }
    }
    /**
     * this method is used to update after a return.
     * if the customer is returning all the tickets they bought for an activity, the available tickets are updated and the number of registered
     *  activities is decremented using the updateAvailableTickets() and decrementNumberPOs() methods respectively. the purchase order is removed from
     *  the arraylist of purchase orders
     * if customer is making a partial return, the number of tickets on the purchase order and the available tickets are updated using the
     *  updatePOTickets() and updateAvailableTickets() methods respectively.
     * @param customerIndex is used to find the customer in the arraylist
     * @param poIndex is used to find the purchase order in the arraylist
     * @param ticketsBought number of tickets customer is buying
     */
    private static void updateCancel(int customerIndex, int poIndex, int ticketsBought, int activityIndex){
        //if customer is returning all the tickets for the activity
        if ((-1 * ticketsBought) == orders.get(poIndex).getTicketsBought()) {
            updateAvailableTickets(ticketsBought, activityIndex);
            decrementNumberPOs(customerIndex);
            orders.remove(poIndex);
        } else { //if customer is making partial return
            //update ticket quantity on the purchase order
            updatePOTickets(poIndex, ticketsBought);
            //update available tickets in activity list
            updateAvailableTickets(ticketsBought, activityIndex);
        }
    }

    /**
     * this method finds the index of the purchase order in the arraylist
     * it constructs a temporary purchase order using the input information
     * it then compares that temporary purchase order against all the purchase orders in the list
     * if a match is found, the index is returned
     * @param c Customer placing the order
     * @param ticketActivityName name of activity for which customer is purchasing tickets
     * @param ticketsBought number of tickets customer is buying
     * @return index of purchase order if found, and -1 if not found
     */
    private static int getPOIndex(Customer c, String ticketActivityName, int ticketsBought) {
        PurchaseOrder po = new PurchaseOrder(c, ticketActivityName, ticketsBought);
        int poIndex = -1;
        for (int i = 0; i<orders.size(); i++) {
                if (orders.get(i).compareTo(po) == 0) {
                    poIndex = i;
            }
        }
        return poIndex;
    }

    /**
     * using purchase order index, update the number of tickets bought by a customer on that purchase order
     * @param poIndex used to find purchase order in arraylist
     * @param ticketsBought number of tickets bought by customer
     */
    private static void updatePOTickets(int poIndex, int ticketsBought){
        //update tickets on customer's purchase order to reflect more tickets bought
        int previousTicketQuantity = orders.get(poIndex).getTicketsBought();
        previousTicketQuantity += ticketsBought;
        orders.get(poIndex).setTicketsBought(previousTicketQuantity);
    }

    /**
     * using activity index, update the number of available tickets for a given activity after purchase or return
     * @param ticketsBought number of tickets bought; used to update number of available tickets
     * @param activityIndex used to find the activity index to get and set the available tickets
     */
    private static void updateAvailableTickets(int ticketsBought, int activityIndex){
        int ticketsAvailable = activities.get(activityIndex).getTicketsAvailable();
        ticketsAvailable -= ticketsBought;
        activities.get(activityIndex).setTicketsAvailable(ticketsAvailable);
    }

    /**
     * clerk inputs number of tickets the customer would like to cancel
     * @param poIndex number of tickets customer has previoulsy purchased for this activity
     * @return number of tickets the customer would like to cancel
     */
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

    /**
     * after buying tickets for a new activity, the customer's number of registered activities is increased
     * @param custIndex used to find the customer in the arraylist
     */
    private static void incrementNumberPOs(int custIndex){
        int numberRegisteredActivities = customerList.get(custIndex).getRegisteredOrders();
        numberRegisteredActivities++;
        customerList.get(custIndex).setRegisteredOrders(numberRegisteredActivities);
    }

    /**
     * after returning all the tickets for n activity, the customer's number of registered activities is decreased
     * @param custIndex used to find the customer in the arraylist
     */
    private static void decrementNumberPOs(int custIndex){
        int numberRegisteredActivities = customerList.get(custIndex).getRegisteredOrders();
        numberRegisteredActivities--;
        customerList.get(custIndex).setRegisteredOrders(numberRegisteredActivities);
    }
}