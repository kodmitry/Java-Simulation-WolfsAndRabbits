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
    public void DoTask(Iterator iterator, LinkedBlockingQueue<Animal> animals) {
        /*System.out.println("Grass.DoTask()");*/
    }
    @Override
    public void SpawnAt(LinkedBlockingQueue<Animal> animals, int x, int y)
    {
        Grass grass = new Grass(50, new Coordinates(x,y));
        animals.add(grass);
    }

    @Override
    public void Jump(Iterator iterator, double Jump_size) throws Exception
    {
        throw new Exception("Grass object should never call .Jump method");
    }
}