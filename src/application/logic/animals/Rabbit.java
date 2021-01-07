package application.logic.animals;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class Rabbit extends Animal {
    private static final int deathThreshold = 20; // when health is below this value, call death method
    private static final float attackRange = 10.0f;
    private static final int attackStrength = 1; // how much damage per tick rabbit is gonna do to enemies
    @Override
    public void DoTask(LinkedBlockingQueue<Animal> animals) {
        Animal grass = findNearbyGrass(animals);
        if (grass != null) {
            if (this.distanceTo(grass) <= (double) attackRange) {
                System.out.println("Rabbit: Grass is in attack range, eating");
                dealDamage(grass, attackStrength);
                health = health + 100;
            } else {
                this.moveTowards(grass, 0.5);
            }
            this.Jump(0.5);
            health--;
            if (health < deathThreshold) {
                System.out.println("Rabbit: died because health == " + health);
                Animal.Kill(this, animals);
            }
        } else {
            this.Jump(2);
        }
    }
    public Rabbit(int health, Coordinates coords) {
        this.health = health;
        this.coords = coords;
    }
    public Animal findNearbyGrass(LinkedBlockingQueue<Animal> animals)
    {
        Animal minAnimal = null;
        double distMin = Double.MAX_VALUE;

        Iterator iterAnimal = animals.iterator();
        Animal animal;
        while (iterAnimal.hasNext())
        {
            animal = (Animal)iterAnimal.next();
            if (animal instanceof Grass)
            {
                double dist = this.distanceTo(animal);
                if (dist < distMin)
                {
                    System.out.println(animal + ", dist = " + dist);
                    System.out.println(iterAnimal);
                    distMin = dist;
                    minAnimal = animal;
                }
            }
        }
        return minAnimal;
    }
}
