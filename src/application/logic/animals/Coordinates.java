package application.logic.animals;

import application.logic.SimulationHandler;

import java.util.Random;

public class Coordinates {
    public double x;
    public double y;
    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public static Coordinates random(Coordinates old, double boundary, int width, int height)
    {
        Random r = new Random();
        double xShift = r.nextDouble() * boundary * 2 - boundary;
        double yShift = r.nextDouble() * boundary * 2 - boundary;
        Coordinates newCoords = new Coordinates(0, 0);
        if (old.x + xShift < width) {
            newCoords.x = old.x + xShift;
        } else {
            newCoords.x = old.x - xShift;
        }
        if (old.y + yShift < height) {
            newCoords.y = old.y + yShift;
        } else {
            newCoords.y = old.y - yShift;
        }
        return newCoords;
    }
}
