package application.logic.animals;

import application.logic.SimulationHandler;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class Animal
{
    public enum TypeOfAnimal {
        RABBIT,
        WOLF,
        GRASS,
        DUMMY
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
        if (type == TypeOfAnimal.DUMMY)
            animal = new Dummy(baseHealth, coords);
        animals.add(animal);

    }

    public void Jump(double Jump_size)
    {
        int width = SimulationHandler.W_WIDTH;
        int height = SimulationHandler.W_HEIGHT;
        this.coords = Coordinates.random(this.coords, Jump_size, width, height);
    }
    public static void Kill(Animal animal, LinkedBlockingQueue<Animal> animals)
    {
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

    public Animal findNearby(TypeOfAnimal type, LinkedBlockingQueue<Animal> animals)
    {
        Animal minAnimal = null;
        double distMin = Double.MAX_VALUE;
        for (Animal animal : animals)
        {
            if (animal instanceof Grass && type == TypeOfAnimal.GRASS ||
                    animal instanceof Wolf && type == TypeOfAnimal.WOLF ||
                    animal instanceof Rabbit && type == TypeOfAnimal.RABBIT)
            {
                double dist = this.distanceTo(animal);
                if (dist < distMin)
                {
                    distMin = dist;
                    minAnimal = animal;
                }
            }
        }
        return minAnimal;
    }
    public static int count(TypeOfAnimal type, LinkedBlockingQueue<Animal> animals) {
        int count = 0;
        for (Animal animal : animals) {
            if (animal instanceof Grass && type == TypeOfAnimal.GRASS ||
                    animal instanceof Wolf && type == TypeOfAnimal.WOLF ||
                    animal instanceof Rabbit && type == TypeOfAnimal.RABBIT) {
                count++;
            }
        }
        return count;
    }
}
