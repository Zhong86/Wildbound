package game.world;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class TileMap {

    private static final int TILE = Entity.TILE;
    private static final String CLEAN = "/assets/tiles/clean/";
    private static final String POISONED = "/assets/tiles/poisoned/";
    public static final int BLOCKED=-1,LAND=0,GRASS=1,GATE=2;

    private final int width,height;
    private final char[][] codes;
    private final int[][] level;
    private final boolean[] cleaned = new boolean[5];

    private BufferedImage[][] pollutedImg, cleanImg;

    public TileMap(String mapPath, String levelsPath) {
        List<String> rows = readLines(mapPath);
        List<String> levelRows = readLines(levelsPath);
        height = rows.size();
        width = rows.get(0).length();

        codes = new char[height][width];
        level = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0;x < width; x++) {
                codes[y][x] = rows.get(y).charAt(x);
                level[y][x] = levelRows.get(y).charAt(x) - '0';
            }
        }
    }

    public static List<String> readLines(String path) {
        try(InputStream in = TileMap.class.getResourceAsStream(path)) {
            if (in == null) throw new IllegalArgumentException("Map file not found on classpath: " + path);

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            List<String> lines = new ArrayList<>();
            String line; 
            while ((line = reader.readLine()) != null) {
                if(!line.isEmpty()) lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            throw new RuntimeException("Could not read: " + path, e);
        }
    }

    private static BufferedImage readImage(String path) {
        try (InputStream in = TileMap.class.getResourceAsStream(path)) {
            if (in == null) throw new IllegalArgumentException("Tile not found on classpath: " + path);
            return ImageIO.read(in);
        } catch (IOException e) {
            throw new RuntimeException("Could not read " + path, e);
        }
    }

    public char charAt(int x, int y) {
        if(x < 0 || y < 0 || x >= width || y >= height) return 'T';
        return codes[y][x];
    }

    public char takeSpawn(int x, int y) {
        char c = charAt(x, y);
        if("SNEB".indexOf(c) < 0) return ' ';
        codes[y][x] = c == 'B'
            ? 'D' : '.';
        return c;
    }

    private static boolean isGate(char c) {
        return c == '1' || c == '2' || c == '3';
    }

    private int owner(int x, int y) {
        char c = codes[y][x];
        return isGate(c) ? c - '0' : level[y][x]; // gate will be as num
    }

    public void buildImages() {
        Map<String, BufferedImage> loaded = new HashMap<>();
        cleanImg = new BufferedImage[height][width];
        pollutedImg = new BufferedImage[height][width]; 

         for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                String ground = groundName(x,y);
                String dirty = isGate(codes[y][x])
                    ? gateName(codes[y][x]) : ground;
                cleanImg[y][x] = loaded.computeIfAbsent(CLEAN + ground + ".png", TileMap::readImage);
                pollutedImg[y][x] = loaded.computeIfAbsent(POISONED+dirty+".png", TileMap::readImage);
            }
        }
    }

     private String groundName(int x, int y) {
        return switch (codes[y][x]) {
            case 'T' -> "tree";
            case 'R' -> "rock";
            case ',' -> "tall_grass";
            case 'f' -> "grass_flowers";
            case 'D' -> "den_ground";
            case 'P', '1', '2', '3' -> "path_" + String.format("%02d", edgeMask(x, y, "P123"));
            case 'W' -> "water_" + String.format("%02d", edgeMask(x, y, "W"));
            default -> "grass";
        };
    }

    private static String gateName(char c) {
        return switch (c) {
            case '1' -> "blocker_garbage";
            case '2' -> "blocker_sludge";
            default -> "blocker_smog";
        };
    }

    private int edgeMask(int x, int y, String sameKinds) {
        int mask = 0;
        if (sameKinds.indexOf(charAt(x, y - 1)) >= 0) mask += 1;
        if (sameKinds.indexOf(charAt(x + 1, y)) >= 0) mask += 2;
        if (sameKinds.indexOf(charAt(x, y + 1)) >= 0) mask += 4;
        if (sameKinds.indexOf(charAt(x - 1, y)) >= 0) mask += 8;
        return mask;
    }

    public int tileAt(int x, int y) {
        char c = charAt(x, y);
        if (isGate(c)) return cleaned[c - '0'] ? LAND : GATE;
        return switch (c) {
            case 'T', 'R', 'W' -> BLOCKED;
            case ',' -> GRASS;
            default -> LAND;
        };
    }
    
    public int gateAt(int x, int y) {
        char c = charAt(x, y);
        return isGate(c) ? c - '0' : 0;
    }

    public int levelAt(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) return 0;
        return level[y][x];
    }

    public void cleanLevel(int n) { cleaned[n] = true; }

    public boolean isCleaned(int n) { return cleaned[n]; }

    public void draw(Graphics2D g) {
        Rectangle view = g.getClipBounds();
        int firstX = 0, firstY=0, lastX=width-1, lastY=height-1;

        if(view != null) {
            firstX = Math.max(0, view.x/TILE);
            firstY = Math.max(0, view.y/TILE); 
            lastX = Math.min(width-1, view.x+view.width/TILE);
            lastY = Math.min(height-1, view.y+view.height/TILE);
        }

        for (int y = firstY; y <= lastY; y++) {
            for (int x = firstX; x <= lastX; x++) {
                BufferedImage img = cleaned[owner(x, y)] ? cleanImg[y][x] : pollutedImg[y][x];
                g.drawImage(img, x * TILE, y * TILE, null);
            }
        }
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getPixelWidth() { return width * TILE; }
    public int getPixelHeight() { return height * TILE; }
}
