package application.logic.animals;

import application.logic.SimulationHandler;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Grass extends Animal {
    private static final int spreadRange = 10;
    private static final int maxReproductionRate = 500;
    private int reproductionRate = 0;
    public Grass(int health, Coordinates coords) {
        Random rand = new Random();
        this.health = health;
        this.coords = coords;
        this.reproductionRate = rand.nextInt(maxReproductionRate);
    }
    @Override
    public void DoTask(LinkedBlockingQueue<Animal> animals) {
        health++;
        if (health <= 0)
        {
            Animal.Kill(this, animals);
        }
        if (reproductionRate >= maxReproductionRate)
        {
            Animal.SpawnAt(
                    TypeOfAnimal.GRASS,
                    animals,
                    Coordinates.random(this.coords, spreadRange, SimulationHandler.W_WIDTH, SimulationHandler.W_HEIGHT),
                    50);
            reproductionRate = 0;
        } else {
            reproductionRate++;
        }
    }
    @Override
    public void Jump(double Jump_size)
    {
        // do nothing
    }
}
