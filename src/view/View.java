package view;
import tile.Tile;
import controller.Controller;


import javax.swing.*;
import java.awt.*;

public class View extends JPanel {
    public boolean isGameWon = false;
    public boolean isGameLost = false;
    public Color BG_COLOR = new Color(0xbbada0);
    public String FONT_NAME = "Arial";
    public int TILE_SIZE = 96;
    public int TILE_MARGIN = 12;

    public Controller controller;

    public View(Controller controller)
    {
        setFocusable(true);
        this.controller = controller;
        addKeyListener(controller);
    }

    public int offsetCoors(int a)
    {
        return (a * (TILE_MARGIN + TILE_SIZE) + TILE_MARGIN);
    }

    public void draw_tile(Graphics g2, Tile tile, int x, int y)
    {
        Graphics2D g = ((Graphics2D)g2);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int value = tile.value;
        int xOffset = offsetCoors(x);
        int yOffset = offsetCoors(y);
        g.setColor(tile.getTileColor());
        g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, 10, 10);
        g.setColor(tile.getFontColor());
        int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
        Font font = new Font(FONT_NAME, Font.BOLD, size);
        g.setFont(font);

        String s = String.valueOf(value);
        FontMetrics fm = getFontMetrics(font);

        int w = fm.stringWidth(s);
        int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

        if (value != 0)
            g.drawString(s, xOffset + (TILE_SIZE - w) / 2, yOffset + TILE_SIZE - (TILE_SIZE - h) / 2 - 2);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        for (int x = 0; x < 4; x++)
            for (int y = 0; y < 4; y++)
                draw_tile(g, controller.getGameTiles()[y][x], x, y);
        if (isGameWon)
            JOptionPane.showMessageDialog(this, "You've won!\n Esc to restart game!");
        else if (isGameLost)
            JOptionPane.showMessageDialog(this, "You've lost\n Esc to restart game!");
    }
}
