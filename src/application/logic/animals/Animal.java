package application.logic.animals;

import application.logic.SimulationHandler;

import java.awt.*;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Animal
{
    public int health;
    public Coordinates coords;
    public abstract void DoTask(LinkedBlockingQueue<Animal> animals);
    public abstract void SpawnAt(LinkedBlockingQueue<Animal> animals, int x, int y);
    public void Jump(double Jump_size)
    {
        int width = SimulationHandler.WINDOW_WIDTH;
        int height = SimulationHandler.WINDOW_HEIGHT;
        Random r = new Random();
        Coordinates old = this.coords;
        Coordinates newCoords;
        do {
            newCoords = new Coordinates(old.x + r.nextDouble() * 2 * Jump_size - Jump_size,old.y +
                    r.nextDouble() * 2 * Jump_size - Jump_size);
        } while(newCoords.x < 0 || newCoords.y < 0 || newCoords.x > width || newCoords.y > height);
        this.coords = newCoords;
    }
    public static void Kill(Animal animal, LinkedBlockingQueue<Animal> animals)
    {
        System.out.println("Animal.Kill() " + animal);
        animals.remove(animal);
    }
    public void moveTowards(Animal target, double speed)
    {
        if (target.coords.x > this.coords.x) {
            this.coords.x = this.coords.x + speed;
        } else {
            this.coords.x = this.coords.x - speed;
        }
        if (target.coords.y > this.coords.y) {
            this.coords.y = this.coords.y + speed;
        } else {
            this.coords.y = this.coords.y - speed;
        }
    }
}
