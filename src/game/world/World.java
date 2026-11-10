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

    private static final String MAP_PATH = "map.txt";
    public final List<Enemy> enemies = new ArrayList<>();
    private char[][] tiles;
    private int worldY;
    private int worldX;

    private static int currentLevel = 0;

    public final List<NPC> npcs = new ArrayList<>();

    public World() {
        tiles = new char[worldX][worldY];
        loadMap(MAP_PATH);
    }

    public void loadMap(String path) {
        List<String> lines; 
        try {
            lines = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Could not load map: " + path, e);
        }

        worldY = lines.size();
        worldX = lines.stream().mapToInt(String::length).max().orElse(0);
        tiles = new char[worldX][worldY];

        for (int y = 0; y < worldY; y++) {
            String line = lines.get(y);
            for (int x = 0; x < worldX; x++) {
                char c = line.charAt(x);
                tiles[x][y] = c;
            }
        }
    }

    public int getPixelWidth() {
        return worldX * Entity.TILE;
    }

    public int getPixelHeight() {
        return worldY * Entity.TILE;
    }

    public int tileAt(int x, int y) {
        if(x<0 || y<0 || x>=worldX || y>=worldY) return -1; 
        else if(tiles[x][y] == '#') return -1; // obstacle
        else if(tiles[x][y] == 'w') return 1; // grass
        else if(tiles[x][y] == '@') return 2; // gate
        else return 0; // normal land
    }

    public NPC npcAt(int x, int y) {
        for(NPC npc: npcs) {
            if(npc.getTileX() == x && npc.getTileY() == y) return npc;
        }
        return null;
    }

    private Color tileFor(char c) {
        return switch (c) {
            case '#' -> new Color(70,70,80);
            case 'w' -> new Color(60,150,60);
            default -> new Color(190, 170,120);
        };
    }

    public void draw(Graphics2D g) {
        final int tileSize = Entity.TILE;

        Rectangle clip = g.getClipBounds();
        int startX = 0, startY = 0, endX = worldX, endY = worldY;
        if (clip != null) {
            startX = Math.max(0, clip.x / tileSize);
            startY = Math.max(0, clip.y / tileSize);
            endX = Math.min(worldX, (clip.x + clip.width) / tileSize+1);
            endY = Math.min(worldY, (clip.y + clip.height) / tileSize+1);
        }

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                g.setColor(tileFor(tiles[x][y]));
                g.fillRect(x*tileSize, y*tileSize, tileSize, tileSize);
            }
        }
    }
}
