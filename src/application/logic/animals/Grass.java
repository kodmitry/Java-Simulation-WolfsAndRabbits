package application.logic.animals;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Grass extends Animal {
    public Grass(int health, Coordinates coords) {
        this.health = health;
        this.coords = coords;
    }
    @Override
    public void DoTask(LinkedBlockingQueue<Animal> animals) {
        /*System.out.println("Grass.DoTask()");*/
    }
    //@Override
/*    public static void SpawnAt(LinkedBlockingQueue<Animal> animals, Coordinates coords, int baseHealth)
    {
        Grass grass = new Grass(baseHealth, coords);
        animals.add(grass);
    }*/

    @Override
    public void Jump(double Jump_size)
    {
        // do nothing
    }
}
