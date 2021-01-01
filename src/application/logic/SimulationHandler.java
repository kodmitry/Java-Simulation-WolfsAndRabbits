package application.logic;

public class SimulationHandler {
    private static SimulData CurrentBestRun;

    public static SimulData SimulateUntilConditions (
            /*Initialized vars*/
        ) {
        // Initialize variables
        InitializeVariables();
        // Start main routine
        int Iterations = 0;
        while (!IsSatisfied() && Iterations < getMaximumIterations())
        {
            // Create separate game
            Iterations++;
        }
        // Return best simulation's data
        return CurrentBestRun;
    }
    private static void InitializeVariables()
    {
        CurrentBestRun.CountOfRabbits = 0;
        CurrentBestRun.CountOfWolfs = 0;
        CurrentBestRun.CountOfGrass = 0;
    }
    private static boolean IsSatisfied()
    {
        return false;
    }
    private static int getMaximumIterations()
    {
        return 1000;
    }


}
