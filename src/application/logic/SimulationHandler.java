package application.logic;
import application.logic.animals.*;
import application.windowInterface.WindowHandler;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class SimulationHandler {
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;
    public static int BestTime;
    public static LinkedBlockingQueue<Animal> animals;
    private static SimulData CurrentBestRun = new SimulData(0,0,0);
    public SimulationHandler()
    {
        CurrentBestRun = new SimulData(0, 0, 0);
    }
    public static SimulData SimulateUntilConditions (int width, int height) throws InterruptedException
    {
        // Initialize variables
        InitializeVariables();
        WINDOW_WIDTH = width;
        WINDOW_HEIGHT = height;
        // Start main routine
        int Iterations = 0;
        SimulData CurrentRunData = new SimulData(30, 2, 10); // TODO : should not be 0 0 0
        // Lets run our simulation routine to figure out the best possible starting variables
        while (!IsSatisfied() && Iterations < getMaximumExperimentsCount())
        {
            // Create separate game
            int TimeOfCurrentRun = RunSimulation(CurrentRunData, width, height);
            if (TimeOfCurrentRun > BestTime)
            {
                BestTime = TimeOfCurrentRun;
                CurrentBestRun = CurrentRunData;
            }
            Iterations++;
            // new starting variables
            CurrentRunData = CurrentRunData.Next();
        }
        // Return best simulation's data
        return CurrentBestRun;
    }
    private static int RunSimulation(SimulData Data, int width, int height) throws InterruptedException
    {
        System.out.println("Running next simulation with rabbits = " + Data.RabbitsCount);
        // Count of tasks = time since this simulation had started
        int CompletedTasksCount = 0;
        // Creating thread-safe queue (FIFO) of all the animals alive on the field
        animals = new LinkedBlockingQueue<Animal>();
        // Adding base amount of animals
        AddAnimalsRandom(Animal.TypeOfAnimal.RABBIT, Data.RabbitsCount, animals, 1000, width, height);
        AddAnimalsRandom(Animal.TypeOfAnimal.WOLF, Data.WolfsCount, animals, 3000, width, height);
        AddAnimalsRandom(Animal.TypeOfAnimal.GRASS, Data.GrassCount, animals, 100, width, height);
        // Main routine
        WindowHandler window = WindowHandler.CreateWindow(width,height);
        while (animals.size() != 0 && CompletedTasksCount < Integer.MAX_VALUE) // TODO: not safe cuz movescount
        {
            CompletedTasksCount++;
            Iterator iterator = animals.iterator();
            while (iterator.hasNext()) {
                Animal animal = (Animal)iterator.next();
                animal.DoTask(animals);
            }
            window.repaint();
            Thread.sleep(10);
        }

        return CompletedTasksCount;
    }
    private static void InitializeVariables()
    {
        CurrentBestRun.RabbitsCount = 0;
        CurrentBestRun.WolfsCount = 0;
        CurrentBestRun.GrassCount = 0;
        BestTime = 0;
    }
    private static boolean IsSatisfied()
    {
        return false;
    }
    private static int getMaximumExperimentsCount()
    {
        return 1;
    }
    private static void AddAnimalsRandom(Animal.TypeOfAnimal type, int n, LinkedBlockingQueue<Animal> animals, int baseHealth, int width, int height) // Adds to random x y coordinates
    {
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            Coordinates coords = new Coordinates(rand.nextInt(width + 1), rand.nextInt(height + 1));
            Animal.SpawnAt(type, animals, coords, baseHealth);
        }
    }
}
