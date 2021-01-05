package application.logic.animals;

import application.windowInterface.WindowHandler;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Wolf extends Animal {
    private static final int maxStamina = 100;
    private static final float baseLethalityRate = 1.0f; // a chance of killing a nearby rabbit when wolf is healthy
    private static final float exhaustedLethalityRate = 0.5f; // same, but when wolf is exhausted
    private static final float attackRange = 1000.0f;
    private static final int deathThreshhold = 20; // when health is below this value, call self destruction

    private int stamina = 0; // for CD in [0,maxStamina) range lets wolf to wonder, CD == maxStamina is rdy to attack
    private int staminaRestoreSpeed = 1; // for staminaRS == 1 wolf is gonna restore 1 point of stamina per tick

    public Wolf(int health, Coordinates coords) {
        this.health = health;
        this.coords = coords;
    }
    @Override
    public void DoTask(Iterator iterator, LinkedBlockingQueue<Animal> animals) {
        if (stamina == maxStamina) {
            /*System.out.println("Wolf is looking for a pray");*/
            Animal pray = findNearbyPray(animals);
            if (pray != null) {
                stamina = 0;
                System.out.println("Wolf: Pray is found, trying to kill, stamina is " + stamina);
                boolean killed = tryToKill(pray, animals);
                if (killed)
                    System.out.println("Killed successful");
             } else {
                /*System.out.println("Wolf: no pray nearby");*/
            }
        } else {
            System.out.println("Wolf: too exhausted, stamina == " + stamina);
            stamina += staminaRestoreSpeed; // restore stamina
        }
        this.Jump(10);
        health--;
        if (health < deathThreshhold) {
            System.out.println("Wolf: died because health == " + health);
            Animal.Kill(this, animals);
        }
    }
    @Override
    public void SpawnAt(LinkedBlockingQueue<Animal> animals, int x, int y)
    {
        Wolf wolf = new Wolf(50, new Coordinates(x,y));
        animals.add(wolf);
    }

    @Override
    public void Jump(double Jump_size)
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
                if (dist < distMin && dist <= (double)attackRange)
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
    public double distanceTo(Animal animal)
    {
        double x1 = this.coords.x;
        double y1 = this.coords.y;
        double x2 = animal.coords.x;
        double y2 = animal.coords.y;
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
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
