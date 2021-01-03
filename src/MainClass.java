import java.util.Scanner;
import application.logic.SimulData;
import application.IOinterface.ConsoleOutput;
import application.logic.SimulationHandler;


public class MainClass {
    public static void main(String[] args) throws InterruptedException {
        // Start new simulation
        SimulData BestRunData = SimulationHandler.SimulateUntilConditions(1280, 780);
        // Print our results
        ConsoleOutput.PrintBestResults(BestRunData);

        System.out.println("Type anything to close this window");
        new Scanner(System.in).next(); // Hold the console from closing
    }
}
