package application.logic.animals;

import application.logic.SimulationHandler;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Grass extends Animal {
    private static final int spreadRange = 8;
    private static final int maxReproductionRate = 500;
    private int reproductionRate = 0;
    public Grass(int health, Coordinates coords) {
        Random rand = new Random();
        this.health = health;
        this.coords = coords;
        this.reproductionRate = rand.nextInt(maxReproductionRate);
    }
    @Override
    public void DoTask(LinkedBlockingQueue<Animal> animals) {
        health++;
        if (health <= 0)
        {
            Animal.Kill(this, animals);
        }
        if (reproductionRate >= maxReproductionRate)
        {
            this.Reproduce(spreadRange, animals);
            reproductionRate = 0;
        } else {
            reproductionRate++;
        }
    }
    @Override
    public void Jump(double Jump_size)
    {
        // do nothing
    }

    public void Reproduce(int range, LinkedBlockingQueue<Animal> animals)
    {
        // Create a list of candidates
        int choiceX = 0;
        int choiceY = 0;
        Random rand = new Random();
        do {
            choiceX = rand.nextInt(3) - 1;
            choiceY = rand.nextInt(3) - 1;
        } while (choiceX == 0 || choiceY == 0);

        ArrayList<Coordinates> L = new ArrayList<>();
        Coordinates cand;
        for (int i = 0; i < 3; i++)
        {
            cand = new Coordinates(this.coords.x - range + range * i, this.coords.y - range);

            if (IsAvailable(cand, animals, range))
            {
                L.add(cand);
            }
        }
        for (int i = 0; i < 3; i++)
        {
            cand = new Coordinates(this.coords.x - range + range * i, this.coords.y + range);
            if (IsAvailable(cand, animals, range))
            {
                L.add(cand);
            }
        }
        cand = new Coordinates(this.coords.x - range, this.coords.y);
        if (IsAvailable(cand, animals, range))
        {
            L.add(cand);
        }
        cand = new Coordinates(this.coords.x + range, this.coords.y);
        if (IsAvailable(cand, animals, range))
        {
            L.add(cand);
        }
        // Pick one randomly
        int size = L.size();
        if (size == 0) {
            return;
        } else {
            int i = rand.nextInt(L.size());
            Coordinates chosen = L.get(i);
            Animal.SpawnAt(
                    TypeOfAnimal.GRASS,
                    animals,
                    chosen,
                    50);
        }
    }
    private boolean IsAvailable(Coordinates candidate, LinkedBlockingQueue<Animal> animals, int range)
    {
        int width = SimulationHandler.W_WIDTH;
        int height = SimulationHandler.W_HEIGHT;
        Animal dummy = new Grass(0, candidate);
        if (candidate.x < 0 || candidate.x > width ||
                candidate.y < 0 || candidate.y > height ||
                (dummy.distanceTo(dummy.findNearby(TypeOfAnimal.GRASS, animals)) < Math.sqrt(2*range^2)))
        {
            return false;

        } else {
            Animal.SpawnAt(TypeOfAnimal.DUMMY, animals, candidate, 50);
            return true;
        }
    }
}
