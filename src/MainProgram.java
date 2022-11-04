import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class MainProgram {

    public static void main(String[] args) {
        String text;
        int numberActivities = 0;
        int numberCustomers;
        ArrayList<String> activities = new ArrayList<String>();
        ArrayList<Integer> ticketsAvailable = new ArrayList<Integer>();
        ArrayList<String> inputArrayList = new ArrayList<String>();
        ArrayList<String> customers = new ArrayList<String>();
        try {
            Scanner inFile = new Scanner(new FileReader("input.txt"));
            while (inFile.hasNextLine()) {
                text=inFile.nextLine();
                numberActivities=parseInt(text);
                for (int i=0; i<numberActivities; i++) {
                    text=inFile.nextLine();
                    activities.add(text);
                    text=inFile.nextLine();
                    ticketsAvailable.add(parseInt(text));
                }
                text=inFile.nextLine();
                numberCustomers=parseInt(text);
                for (int j=0; j<numberCustomers; j++) {
                    //text = inFile.next();
                    customers.add(inFile.nextLine());
                }
            }
            /* simply reading in everything
            while (inFile.hasNextLine()) {
                text = inFile.nextLine();
                inputArrayList.add(text);
            }

             */
        }
        catch (FileNotFoundException e){
            System.out.println("Sorry, invalid file path.");
        }
        /*for (String w: inputArrayList) {
            System.out.println(w);
        }
        numberActivities = parseInt(inputArrayList.get(0));

         */
        for (String name : customers){
            System.out.println("Customer: " + name);
        }
        for (int j = 0; j< numberActivities; j++){
            System.out.println(ticketsAvailable.get(j) + " tickets available for " + activities.get(j));
        }
    }
}
