package application.logic.animals;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Rabbit extends Animal {
    @Override
    public void DoTask(Iterator iterator, LinkedBlockingQueue<Animal> animals) {
        this.Jump(iterator, 1);
        this.health = this.health - 1;
        //System.out.println("New health == " + this.health);
        if (health % 50 == 0) {
            //System.out.println("Creating new rabbit");
            //this.Reproduce(animals);
        }

    }
    public Rabbit(int health, Coordinates coords) {
        this.health = health;
        this.coords = coords;
    }
    @Override
    public void SpawnAt(LinkedBlockingQueue<Animal> animals, int x, int y)
    {
        Rabbit rabbit = new Rabbit(50, new Coordinates(x,y));
        animals.add(rabbit);
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
