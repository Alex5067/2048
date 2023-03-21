import controller.Controller;
import box.Box;

import javax.swing.*;

public class Main {
    public static void main(String[] args)
    {
        Box box = new Box();
        Controller controller = new Controller(box);
        JFrame game = new JFrame();

        game.setTitle("2048");
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setSize(450, 500);
        game.setResizable(false);

        game.add(controller.getView());
        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }
}
