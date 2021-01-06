package application.logic.animals;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Rabbit extends Animal {
    private static final int deathThreshhold = 20; // when health is below this value, call death method
    private static final int maxStamina = 100;
    private static final float attackRange = 10.0f;
    private final int staminaRestoreSpeed = 1; // for staminaRS == 1 wolf is gonna restore 1 point of stamina per tick
    private int stamina = 0; // for CD in [0,maxStamina) range lets wolf to wonder, CD == maxStamina is rdy to attack
    @Override
    public void DoTask(LinkedBlockingQueue<Animal> animals) {
        if (stamina == maxStamina) {
            Animal grass = findNearbyGrass(animals);
            if (grass != null) {
                if (this.distanceTo(grass) <= (double)attackRange) {
                    stamina = 0;
                    System.out.println("Rabbit: Grass is in attack range, eating, stamina is " + stamina);
                    Kill(grass, animals);
                    System.out.println("Eaten successful");
                    health = health + 100;
                } else {
                    this.moveTowards(grass, 0.5);
                }
            } else {
                /*System.out.println("Wolf: no alive prays");*/
            }
        } else {
            System.out.println("Wolf: too exhausted, stamina == " + stamina);
            stamina += staminaRestoreSpeed; // restore stamina
        }
        this.Jump(0.5);
        health--;
        if (health < deathThreshhold) {
            System.out.println("Wolf: died because health == " + health);
            Animal.Kill(this, animals);
        }
    }
    public Rabbit(int health, Coordinates coords) {
        this.health = health;
        this.coords = coords;
    }
    //@Override
/*    public static void SpawnAt(LinkedBlockingQueue<Animal> animals, Coordinates coords, int baseHealth)
    {
        Rabbit rabbit = new Rabbit(baseHealth, coords);
        animals.add(rabbit);
    }*/
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
