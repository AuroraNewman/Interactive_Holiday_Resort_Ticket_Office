import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class MainProgram {

    public static void main(String[] args) {
        String text;
        int numberActivities;
        ArrayList<String> inputArrayList = new ArrayList<String>();
        try {
            Scanner inFile = new Scanner(new FileReader("input.txt"));
            while (inFile.hasNextLine()) {
                text = inFile.nextLine();
                inputArrayList.add(text);
            }
        }
        catch (Exception e){
            System.out.println("Sorry, invalid file path.");
        }
        for (String w: inputArrayList) {
            System.out.println(w);
        }
        numberActivities = Integer.parseInt(inputArrayList.get(0));
    }
}
