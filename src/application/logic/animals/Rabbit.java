package application.logic.animals;

import java.util.concurrent.LinkedBlockingQueue;

public class Rabbit extends Animal {
    private static final int deathThreshold = 20; // when health is below this value, call death method
    private static final float attackRange = 10.0f;
    private static final int attackStrength = 1; // how much damage per tick rabbit is gonna do to enemies
    private static final int maxReproductionRate = 300;
    private int reproductionRate = 0;
    @Override
    public void DoTask(LinkedBlockingQueue<Animal> animals) {
        Animal grass = findNearby(TypeOfAnimal.GRASS, animals);
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
}
