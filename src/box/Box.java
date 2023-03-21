package box;
import tile.Tile;
import java.util.*;


public class Box{

    public static final int FIELD_WIDTH = 4;
    public Tile[][] gameTiles;
    public int max_tile = 2;

    public Box()
    {
        resetGameTiles();
    }

    public void addTile()
    {
        List<Tile> emptyTiles = getEmptyTiles();
        if (!emptyTiles.isEmpty())
        {
            int randomTileIndex = (int) (Math.random() * emptyTiles.size());
            emptyTiles.get(randomTileIndex).value = (Math.random() < 0.9) ? 2 : 4;
        }
    }

    public List<Tile> getEmptyTiles()
    {
        List<Tile> emptyTiles = new ArrayList<>();
        for (int i = 0; i < FIELD_WIDTH; i++)
            for (int j = 0; j < FIELD_WIDTH; j++)
                if (gameTiles[i][j].isEmpty())
                    emptyTiles.add(gameTiles[i][j]);
        return emptyTiles;
    }

    public Tile[][] getGameTiles()
    {
        return gameTiles;
    }

    public void resetGameTiles()
    {
        this.gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++)
            for (int j = 0; j < FIELD_WIDTH; j++)
                gameTiles[i][j] = new Tile();
        addTile();
        addTile();
    }

    public boolean canMove()
    {
        if (!getEmptyTiles().isEmpty())
            return true;
        for (int i = 0; i < gameTiles.length; i++)
            for (int j = 1; j < gameTiles.length; j++)
                if (gameTiles[i][j].value == gameTiles[i][j - 1].value)
                    return true;

        for (int j = 0; j < gameTiles.length; j++)
            for (int i = 1; i < gameTiles.length; i++)
                if (gameTiles[i][j].value == gameTiles[i - 1][j].value)
                    return true;
        return false;
    }

    public boolean mergeTiles(Tile[] tiles)
    {
        Tile[] clone = tiles.clone();
        for (int i = 1; i < tiles.length; i++)
        {
            if ((tiles[i - 1].value == tiles[i].value) && !tiles[i - 1].isEmpty() && !tiles[i].isEmpty())
            {
                tiles[i - 1].value *= 2;
                if (tiles[i - 1].value > max_tile)
                    max_tile = tiles[i - 1].value;
                tiles[i] = new Tile();
                compressTiles(tiles);
            }
        }
        for (int i = 0; i < clone.length; i++)
            if (clone[i].value != tiles[i].value)
                return true;
        return false;
    }

    public boolean compressTiles(Tile[] tiles)
    {
        Tile[] clone = tiles.clone();
        for (int i = 0; i < tiles.length; i++)
        {
            if ((tiles[i].value == 0) && i < tiles.length - 1 && tiles[i + 1].value != 0)
            {
                Tile temp = tiles[i];
                tiles[i] = tiles[i + 1];
                tiles[i + 1] = temp;
                i = -1;
            }
        }
        for (int i = 0; i < clone.length; i++)
            if (clone[i].value != tiles[i].value)
                return true;
        return false;
    }

    public void left()
    {
        boolean isChanged = false;
        for (int i = 0; i < FIELD_WIDTH; i++)
            if (compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i]))
                isChanged = true;
        if (isChanged)
            addTile();
    }

    public void rotate_left()
    {
        for (int i = 0; i < FIELD_WIDTH / 2; i++)
            for (int j = i; j < FIELD_WIDTH - 1 - i; j++)
            {
                Tile tmp = gameTiles[i][j];
                gameTiles[i][j] = gameTiles[j][FIELD_WIDTH - 1 - i];
                gameTiles[j][FIELD_WIDTH - 1 - i] = gameTiles[FIELD_WIDTH - 1 - i][FIELD_WIDTH - 1 - j];
                gameTiles[FIELD_WIDTH - 1 - i][FIELD_WIDTH - 1 - j] = gameTiles[FIELD_WIDTH - 1 - j][i];
                gameTiles[FIELD_WIDTH - 1 - j][i] = tmp;
            }
    }

    public void right()
    {
        rotate_left();
        rotate_left();
        left();
        rotate_left();
        rotate_left();
    }

    public void up()
    {
        rotate_left();
        left();
        rotate_left();
        rotate_left();
        rotate_left();
    }

    public void down()
    {
        rotate_left();
        rotate_left();
        rotate_left();
        left();
        rotate_left();
    }
}
