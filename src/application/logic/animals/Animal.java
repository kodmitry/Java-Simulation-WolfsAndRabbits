package application.logic.animals;

import application.logic.SimulationHandler;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Animal
{
    public enum TypeOfAnimal {
        RABBIT,
        WOLF,
        GRASS
    }
    public int health;
    public Coordinates coords;
    public abstract void DoTask(LinkedBlockingQueue<Animal> animals);

    public static void SpawnAt(TypeOfAnimal type, LinkedBlockingQueue<Animal> animals, Coordinates coords, int baseHealth)
    {
        Animal animal = null;
        if (type == TypeOfAnimal.GRASS)
            animal = new Grass(baseHealth, coords);
        if (type == TypeOfAnimal.RABBIT)
            animal = new Rabbit(baseHealth, coords);
        if (type == TypeOfAnimal.WOLF)
            animal = new Wolf(baseHealth, coords);
        animals.add(animal);
    }

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

    public double distanceTo(Animal animal)
    {
        double x1 = this.coords.x;
        double y1 = this.coords.y;
        double x2 = animal.coords.x;
        double y2 = animal.coords.y;
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    public void dealDamage(Animal animal, int damage)
    {
        animal.health = animal.health - damage;
    }
}
