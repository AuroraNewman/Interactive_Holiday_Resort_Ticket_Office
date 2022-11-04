import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class MainProgram {
    //private static Activity activity1 = new Activity();
    public static void main(String[] args) {
        TicketOffice.readFile();
        for (Activity a : activityArrayList)
            System.out.println(a);
    }
}
