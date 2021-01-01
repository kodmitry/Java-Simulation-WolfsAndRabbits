package application.logic.animals;

import java.util.Iterator;
import java.util.Random;

public class Rabbit extends Animal {
    @Override
    public void DoTask(Iterator iterator) {
        this.Jump(iterator, 1);
        this.health = this.health - 1;
        System.out.println("New health == " + this.health);
        if (health <= 0) {
            System.out.println("Death by health <= 0");
            this.Kill(iterator);
        }

    }
    public Rabbit(int health, Coordinates coords) {
        this.health = health;
        this.coords = coords;
    }
    @Override
    public void Reproduce()
    {

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
