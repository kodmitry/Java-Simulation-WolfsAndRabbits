package application.windowInterface;

import application.logic.SimulationHandler;
import application.logic.animals.Animal;
import application.logic.animals.Rabbit;
import application.logic.animals.Wolf;

import java.awt.*;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowHandler extends JPanel {
    enum AnimalColors {
        RABBIT(Color.orange), WOLF(Color.red);
        private Color color;
        AnimalColors(Color color)
        {
            this.color = color;
        }
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
            if (animal instanceof Rabbit) {
                g2d.setColor(AnimalColors.RABBIT.color);
            }
            if (animal instanceof Wolf) {
                g2d.setColor(AnimalColors.WOLF.color);
            }
            g2d.fillOval((int)animal.coords.x, (int)animal.coords.y, 7, 7);
        }
    }

    public static WindowHandler CreateWindow(int width, int height){
        JFrame frame = new JFrame("WolfsAndRabbits");
        WindowHandler game = new WindowHandler();
        frame.add(game);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return game;
    }
}