# csc8012-summative-coursework
summative coursework for CSC 8012

Scenario: 
Task 1
Derive a SortedArrayList<E> class as a subclass of the java.util.ArrayList<E> class in such a way that the items of a sorted ArrayList are sorted in ascending order. This subclass of the ArrayList<E> class will be needed to complete Task 2 (see Additional Assumptions (2) below). You only need to provide your new insertion method in the SortedArrayList<E> class. You do not need to consider the other methods from the ArrayList<E> class that can modify the list.

Task 2
You are asked to write a program to help a Holiday Resort ticket office clerk in their work. The Holiday Resort offers various activities to its customers. Customers can book tickets to take part in activities, but the number of tickets available for each activity is limited. Your program should read a list of available activities and a list of registered customers from a file. The content of the input file should have the following form:
The first line contains an integer representing the number of available activities, and is followed by the information about the activities (two lines for every activity: one line containing the name of the activity and the second one containing the number of tickets available for this activity). The next line contains an integer representing the number of registered customers, followed by the information about the customers (one line for every customer with their first name and surname). Example file content is given below (same as the input.txt file available from Canvas), but your program should run correctly on any file in the correct format:
6
Hiking
4
Bowling
8
Cycling
10
Sailing
3
Football
22
Table Tennis
4
3
Michael Smith
Anna Smith
Ted Jones

The program should be able to store information about activity and customers:
1. For each activity, the information required is: the name of the activity and the number of tickets left for this activity.
2. For each customer, the office should know their first name, surname and the chosen activity together with the number of tickets bought for each of the activities. A customer can hold tickets for at most three different activities at a time. We also assume that no two customers share both their first name and their surname.
After the initial information has been read from the file, the clerk will work with the program interactively.
The program should display a menu on the screen offering a choice of possible operations, each represented by a lower case letter:
• f - to finish running the program.
• a - to display on the screen information about all the activities.
• c - to display on the screen information about all the customers.
• t - to update the stored data when tickets are bought by one of the registered customers.
• r - to update the stored data when a registered customer cancels tickets for a booking.
You should implement all the above operations.
Additional Assumptions
1. When f is selected, the program stops running and all the data is lost. The program could be extended to save all the data to a file, but this is not part of the project and you must not do this!
2. To store activities and customers you should use your SortedArrayList<E> class. Activities should be sorted in the ascending (lexicographic) order of names of activities. Customers should be sorted in the ascending (lexicographic) order of their surnames. If two customers have the same surname, then their first names should decide their order. You may assume that each customer has exactly one first name and exactly one surname.
3. When tickets for an activity are ordered by a customer, your program must check whether the customer is a valid customer, and that the activity is on the list of the available activities. If not, an appropriate message should be displayed on the screen. If the request is a valid one, the program should check whether there are enough tickets left for the ordered activity. You may assume that in one transaction a customer orders tickets for one activity. If the tickets are not available (or there are not enough tickets), a note (in the form of a letter) should be printed to a file informing the customer that the tickets are not available; no other errors should be logged to letters.txt. You may assume that the order is either fully satisfied or not carried out at all. If the ordered number of tickets is available, the transaction should be processed and the stored information should be updated accordingly.
4. When tickets (a ticket) are (is) cancelled by a customer, your program must check whether the customer is a valid customer, whether the activity specified by the tickets is on the list of activities and whether the tickets have been purchased by this customer. If not, an appropriate message should be displayed on the screen. If the request is a valid one, the stored information should be updated accordingly. You may assume that a customer can cancel tickets for only one activity per one transaction. If a customer has tickets for an activity, they should be able to cancel one, some or all of their tickets for that activity in a single transaction (your program should ask them how many tickets they want to cancel).
5. We assume that the ticket office only sells the tickets initially allocated to it, serves only its registered customers and processes transactions sequentially, one after the other.
6. When the program first starts, you should assume that no tickets have been sold so far.

Objectives: Demonstrate understanding of interfaces, inheritance, and generic classes in Java
