package application.IOinterface;
import application.logic.SimulData;

public class ConsoleOutput
{
    public static void PrintBestResults(SimulData Data)
    {
        System.out.println("The best results were: " + Data.RabbitsCount + " rabbits, " + Data.WolfsCount + " wolfs, " +
                Data.GrassCount + " grass");
     }
}
