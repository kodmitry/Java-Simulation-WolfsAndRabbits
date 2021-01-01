package application.logic.animals;

import java.util.Iterator;

public abstract class Animal
{
    public int health;
    public Coordinates coords;
    public abstract void DoTask(Iterator iterator);
    public abstract void Reproduce();
    public abstract void Kill(Iterator iterator);
    public abstract void Jump(Iterator iterator, double Jump_size);
}
