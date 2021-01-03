package application.windowInterface;

import application.logic.SimulationHandler;
import application.logic.animals.Animal;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowHandler extends JPanel {

    int x = 0;
    int y = 0;

    private void moveBall() {
        x = x + 1;
        y = y + 1;
    }

    @Override
    public void paint(Graphics g) {
        LinkedBlockingQueue<Animal> animals = SimulationHandler.animals;
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Iterator iterator = animals.iterator();
        while (iterator.hasNext()) {
            Animal animal = (Animal)iterator.next();
            g2d.fillOval((int)animal.coords.x, (int)animal.coords.y, 10, 10);
        }
    }

    public static WindowHandler CreateWindow() throws InterruptedException {
        JFrame frame = new JFrame("WolfsAndRabbits");
        WindowHandler game = new WindowHandler();
        frame.add(game);
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return game;
    }
}