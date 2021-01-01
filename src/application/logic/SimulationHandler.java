package application.logic;
import application.logic.animals.Animal;
import application.logic.animals.Rabbit;
import application.logic.animals.Coordinates;
import java.util.ArrayDeque;
import java.util.Iterator;

public class SimulationHandler {
    private static SimulData CurrentBestRun = new SimulData(0,0,0);
    public SimulationHandler()
    {
        CurrentBestRun = new SimulData(0, 0, 0);
    }
    public static SimulData SimulateUntilConditions (
            /*Initialized vars*/
        )
    {
        // Initialize variables
        InitializeVariables();
        // Start main routine
        int Iterations = 0;
        int BestTime = 0;
        SimulData CurrentRunData = new SimulData(100, 0, 0); // TODO : should not be 0 0 0
        // Lets run our simulation routine to figure out the best possible starting variables
        while (!IsSatisfied() && Iterations < getMaximumExperimentsCount())
        {
            // Create separate game with new starting variables
            CurrentRunData = CurrentRunData.Next();
            int TimeOfCurrentRun = RunSimulation(CurrentRunData);
            if (TimeOfCurrentRun > BestTime)
            {
                BestTime = TimeOfCurrentRun;
                CurrentBestRun = CurrentRunData;
            }
            Iterations++;
        }
        // Return best simulation's data
        return CurrentBestRun;
    }
    private static int RunSimulation(SimulData Data)
    {
        System.out.println("Running next simulation with rabbits = " + Data.RabbitsCount);
        // Count of tasks = time since this simulation had started
        int CompletedTasksCount = 0;
        // Creating queue (FIFO) of all the animals alive on the field
        ArrayDeque<Animal> animals= new ArrayDeque<Animal>();
        // Adding base amount of animals
        QueueAddRabbits(Data.RabbitsCount, animals, 50);
        // Main routine
        while (animals.size() != 0 && CompletedTasksCount < Integer.MAX_VALUE) // TODO: not safe cuz movescount
        {
            CompletedTasksCount++;
            Iterator iterator = animals.iterator();
            while (iterator.hasNext()) {
                Animal animal = (Animal)iterator.next();
                animal.DoTask(iterator);
            }
        }

        return CompletedTasksCount;
    }
    private static void InitializeVariables()
    {
        CurrentBestRun.RabbitsCount = 0;
        CurrentBestRun.WolfsCount = 0;
        CurrentBestRun.GrassCount = 0;
    }
    private static boolean IsSatisfied()
    {
        return false;
    }
    private static int getMaximumExperimentsCount()
    {
        return 10;
    }
    private static void QueueAddRabbits(int n, ArrayDeque<Animal> animals, int baseHealth) // Adds to random x y coordinates
    {
        for (int i = 0; i < n; i++) {
            Rabbit rabbit = new Rabbit(baseHealth, new Coordinates(0, 0)); // TODO : make it random x y
            animals.add(rabbit);
        }
    }


}
