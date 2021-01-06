package application.logic.animals;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Wolf extends Animal {
    private static final int maxStamina = 100;
    private static final float baseLethalityRate = 0.5f; // a chance of killing a nearby rabbit when wolf is healthy
    private static final float exhaustedLethalityRate = 0.5f; // same, but when wolf is exhausted
    private static final float attackRange = 10.0f;
    private static final int deathThreshhold = 20; // when health is below this value, call self destruction

    private int stamina = 0; // for CD in [0,maxStamina) range lets wolf to wonder, CD == maxStamina is rdy to attack
    private final int staminaRestoreSpeed = 1; // for staminaRS == 1 wolf is gonna restore 1 point of stamina per tick

    public Wolf(int health, Coordinates coords) {
        this.health = health;
        this.coords = coords;
    }
    @Override
    public void DoTask(LinkedBlockingQueue<Animal> animals) {
        if (stamina == maxStamina) {
            Animal pray = findNearbyPray(animals);
            if (pray != null) {
                if (this.distanceTo(pray) <= (double)attackRange) {
                    stamina = 0;
                    System.out.println("Wolf: Pray is found, trying to kill, stamina is " + stamina);
                    boolean killed = tryToKill(pray, animals);
                    if (killed)
                    {
                        System.out.println("Killed successful");
                        health = health + 100;
                    }
                } else {
                    this.moveTowards(pray, 3);
                }
             } else {
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
    public Animal findNearbyPray(LinkedBlockingQueue<Animal> animals)
    {
        Animal minAnimal = null;
        double distMin = Double.MAX_VALUE;

        Iterator iterAnimal = animals.iterator();
        Animal animal;
        while (iterAnimal.hasNext())
        {
            animal = (Animal)iterAnimal.next();
            if (animal instanceof Rabbit)
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

    public boolean tryToKill(Animal animal, LinkedBlockingQueue<Animal> animals)
    {
        System.out.println("Wolf.tryToKill");
        Random rand = new Random();
        float rolled = rand.nextFloat();
        float lethality;
        if (health < 50) {
            lethality = exhaustedLethalityRate;
        } else {
            lethality = baseLethalityRate;
        }
        System.out.println("Wolf: rolled = " + rolled + " lethality = " + lethality + " wolf's health: " + health);
        if (rolled < lethality) {
            Animal.Kill(animal, animals);
            return true;
        }
        return false;
    }
}
