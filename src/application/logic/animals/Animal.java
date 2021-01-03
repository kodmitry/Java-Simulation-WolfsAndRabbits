package application.logic.animals;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Animal
{
    public int health;
    public Coordinates coords;
    public abstract void DoTask(Iterator iterator, LinkedBlockingQueue<Animal> animals);
    public abstract void SpawnAt(LinkedBlockingQueue<Animal> animals, int x, int y);
    public abstract void Kill(Iterator iterator);
    public abstract void Jump(Iterator iterator, double Jump_size) throws Exception;
}
