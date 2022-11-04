import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class MainProgram {
    private static Activity activity1 = new Activity();
    /*shouldn't need the following section; i used it for the input file as three arraylists
    ArrayList<String> activities = new ArrayList<String>();
    ArrayList<Integer> ticketsAvailable = new ArrayList<Integer>();
    ArrayList<String> inputArrayList = new ArrayList<String>();
    ArrayList<String> customers = new ArrayList<String>();

     */

    public static void main(String[] args) {
    }

    private static Activity readFile() {
        String text;
        int numberCustomers;
        int numberActivities = 0;
        try {
            Scanner inFile = new Scanner(new FileReader("input.txt"));
            while (inFile.hasNextLine()) {
                text=inFile.nextLine();
                numberActivities=parseInt(text);
                for (int i=0; i<numberActivities; i++) {
                    text=inFile.nextLine();
                    //activities.add(text);
                    //change this to setting the activity name in object activity
                    text=inFile.nextLine();
                    //ticketsAvailable.add(parseInt(text));
                    //change this to setting the #available tix in object activity
                }
                text=inFile.nextLine();
                numberCustomers=parseInt(text);
                for (int j=0; j<numberCustomers; j++) {
                    //text = inFile.next();
                    //customers.add(inFile.nextLine());
                    //make this one make a customer
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
    }
}
