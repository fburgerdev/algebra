package algebra;

import java.util.Scanner;

public class CLI {
    // run
    public static void run() {
        Scanner in = new Scanner(System.in);

        System.out.print("enter expression: ");
        String str = in.nextLine();
        System.out.println("minimized expression: " + new Minimizer(Parser.parse(str)).findMinimum().str());
    }
}
