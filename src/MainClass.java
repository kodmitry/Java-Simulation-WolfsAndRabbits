import java.util.Scanner;

import application.logic.SimulationHandler;

public class MainClass {
    public static void main(String[] args) {
        // Start new simulation
        SimulationHandler.SimulateUntilConditions();
        System.out.println("Type anything to close this window");
        new Scanner(System.in).next(); // Hold the console from closing
    }
}
