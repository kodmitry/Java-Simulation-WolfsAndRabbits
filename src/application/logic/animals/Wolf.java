package application.logic.animals;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Wolf extends Animal {
    public Wolf(int health, Coordinates coords) {
        this.health = health;
        this.coords = coords;
    }
    @Override
    public void DoTask(Iterator iterator, LinkedBlockingQueue<Animal> animals) {
        this.Jump(iterator, 3);
        System.out.println("Wolf has jumped");
    }
    @Override
    public void SpawnAt(LinkedBlockingQueue<Animal> animals, int x, int y)
    {
        Wolf wolf = new Wolf(50, new Coordinates(x,y));
        animals.add(wolf);
    }

    @Override
    public void Kill(Iterator iterator)
    {
        iterator.remove();
    }

    @Override
    public void Jump(Iterator iterator, double Jump_size)
    {
        Random r = new Random();
        Coordinates old = this.coords;
        Coordinates newCoords;
        do {
            newCoords = new Coordinates(old.x + r.nextDouble() * 2 * Jump_size - Jump_size,old.y +
                    r.nextDouble() * 2 * Jump_size - Jump_size);
        } while(newCoords.x < 0 || newCoords.y < 0);
        this.coords = newCoords;
    }
}
