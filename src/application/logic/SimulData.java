package application.logic;

public class SimulData {
    public int RabbitsCount;
    public int WolfsCount;
    public int GrassCount;
    public SimulData(int CountOfRabbits, int CountOfWolfs, int CountOfGrass)
    {
        this.RabbitsCount = CountOfRabbits;
        this.WolfsCount = CountOfWolfs;
        this.GrassCount = CountOfGrass;
    }
    public SimulData Next()
    {
        this.GrassCount++;
        this.RabbitsCount++;
        this.WolfsCount++;
        return this;
    }
}
