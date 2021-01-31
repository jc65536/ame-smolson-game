import javax.swing.*;
import java.awt.*;

public class Main {

    public static final int MS = 1000000;
    public static final int TICK_DURATION = 10 * MS;

    public static void main(String[] args) throws Exception {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        KeyboardManager k = new KeyboardManager();
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(k);

        Scene scene = new Scene(k);
        new Player(scene);

        window.add(scene);
        window.pack();
        window.setVisible(true);

        long lastTime = System.nanoTime();
        long timePassed = 0;
        while (true) {
            long now = System.nanoTime();
            timePassed += now - lastTime;
            lastTime = now;
            while (timePassed >= TICK_DURATION) {
                scene.repaint();
                timePassed -= TICK_DURATION;
            }
        }
    }

}