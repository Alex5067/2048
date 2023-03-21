package controller;

import view.View;
import tile.Tile;
import box.Box;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter
{

    public Box box;
    public View view;
    public int WINNING_TILE = 2048;

    public Controller(Box box)
    {
        this.box = box;
        this.view = new View(this);
    }

    public Tile[][] getGameTiles()
    {
        return box.getGameTiles();
    }

    public void reset_game()
    {
        view.isGameLost = false;
        view.isGameWon = false;
        box.resetGameTiles();
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            reset_game();
        if (!box.canMove())
            view.isGameLost = true;
        if (!view.isGameLost && !view.isGameWon)
        {
            if (e.getKeyCode() == KeyEvent.VK_LEFT)
                box.left();
            if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                box.right();
            if (e.getKeyCode() == KeyEvent.VK_UP)
                box.up();
            if (e.getKeyCode() == KeyEvent.VK_DOWN)
                box.down();
        }
        if (box.max_tile == WINNING_TILE)
            view.isGameWon = true;
        view.repaint();
    }
    public View getView()
    {
        return view;
    }
}
