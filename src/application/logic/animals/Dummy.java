package application.logic.animals;

import java.util.concurrent.LinkedBlockingQueue;

public class Dummy extends Animal{
    private int health = 5;
    public void DoTask(LinkedBlockingQueue<Animal> animals)
    {
        health --;
        if (health <= 0)
        {
            Animal.Kill(this, animals);
        }
    }
    public Dummy(int baseHealth, Coordinates coords)
    {
        this.health = baseHealth;
        this.coords = coords;
    }

}
