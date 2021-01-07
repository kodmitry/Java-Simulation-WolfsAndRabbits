package application.logic.animals;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Wolf extends Animal {
    private static final int maxStamina = 100;
    private static final float baseLethalityRate = 0.8f; // a chance of killing a nearby rabbit when wolf is healthy
    private static final float exhaustedLethalityRate = 0.5f; // same, but when wolf is exhausted
    private static final float attackRange = 10.0f;
    private static final int deathThreshold = 20; // when health is below this value, call self destruction
    private static final int maxReproductionRate = 300;
    private static final int staminaRestoreSpeed = 1; // for staminaRS == 1 wolf is gonna restore 1 point of stamina per tick

    private int stamina = 0; // for CD in [0,maxStamina) range lets wolf to wonder, CD == maxStamina is rdy to attack
    private int reproductionRate = 0;

    public Wolf(int health, Coordinates coords) {
        this.health = health;
        this.coords = coords;
        this.stamina = 100;
    }
    @Override
    public void DoTask(LinkedBlockingQueue<Animal> animals) {
        if (stamina == maxStamina) {
            Animal pray = findNearby(TypeOfAnimal.RABBIT, animals);
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
        if (health < deathThreshold) {
            System.out.println("Wolf: died because health == " + health);
            Animal.Kill(this, animals);
        }
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
