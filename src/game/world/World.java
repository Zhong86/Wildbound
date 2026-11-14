package game.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.colorchooser.ColorChooserComponentFactory;

public class World {

    private static final String MAP_PATH = "/assets/maps/map.txt";
    private static final String LEVELS_PATH = "/assets/maps/levels.txt";

    private final TileMap map;
    public final List<NPC> npcs = new ArrayList<>();
    public final List<Enemy> enemies = new ArrayList<>();

    private int startX, startY;

    private static int currentLevel = 0;

    public World() {
        map = new TileMap(MAP_PATH, LEVELS_PATH);

         for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                switch (map.takeSpawn(x, y)) {
                    case 'S' -> { startX = x; startY = y; }
                    case 'N' -> { }
                    case 'E' -> { }
                    case 'B' -> { }
                }
            }
        }
        map.buildImages();
    }

    public int tileAt(int x, int y) {
        if(npcAt(x, y) != null) return TileMap.BLOCKED;
        return map.tileAt(x, y);
    }

    public NPC npcAt(int x, int y) {
        for(NPC npc: npcs) {
            if(npc.getTileX() == x && npc.getTileY() == y) return npc;
        }
        return null;
    }

    public int getStartX() { return startX; }
    public int getStartY() { return startY; }
    public int getPixelWidth() { return map.getPixelWidth(); }
    public int getPixelHeight() { return map.getPixelHeight(); }

    public void draw(Graphics2D g) {
        map.draw(g);
        for (NPC npc : npcs) npc.draw(g);
        for (Enemy enemy: enemies) enemy.draw(g);
    }
}
