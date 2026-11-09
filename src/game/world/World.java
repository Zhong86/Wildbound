package game.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class World {

    public final List<Enemy> enemies = new ArrayList<>();
    private final char[][] tiles;
    private static final int WORLD_X = 50;
    private static final int WORLD_Y = 50;

    public final List<NPC> npcs = new ArrayList<>();

    public World() {
        tiles = new char[WORLD_X][WORLD_Y];
    }

    public int getPixelWidth() {
        return WORLD_X * Entity.TILE;
    }

    public int getPixelHeight() {
        return WORLD_Y * Entity.TILE;
    }

    public int canWalk(int x, int y) {
        if(x<0 || y<0 || x>=WORLD_X || y>=WORLD_Y) return -1; 
        else if(tiles[x][y] == '#') return -1; // obstacle
        else if(tiles[x][y] == 'w') return 1; // grass
        else return 0; // normal land
    }

    public NPC npcAt(int x, int y) {
        for(NPC npc: npcs) {
            if(npc.getTileX() == x && npc.getTileY() == y) return npc;
        }
        return null;
    }

    public void draw(Graphics2D g) {
        for (int x = 0; x < WORLD_X; x++) {
            for (int y = 0; y < WORLD_Y; y++) {

                // checkerboard tmp
                Color c = (x+y) % 2 == 0 
                    ? new Color(110,190,90)
                    : new Color(90,170,70);
                g.setColor(c);
                g.fillRect(x * Entity.TILE, y * Entity.TILE,
                    Entity.TILE, Entity.TILE);
            }
        }
    }
}
