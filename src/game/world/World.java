package game.world;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class World {

    public final List<Enemy> enemies = new ArrayList<>();
    private final int[][] tiles;
    private static final int WORLD_X = 50;
    private static final int WORLD_Y = 50;

    public World() {
        tiles = new int[WORLD_X][WORLD_Y];

    }

    public boolean canWalk(int x, int y) {
        // check boundaries

        return true;
    }

    public void draw(Graphics2D g) {
        // draw tiles
    }
}
