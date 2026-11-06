package game;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class SpriteSheet {

    private final BufferedImage[][] frames; 

    public SpriteSheet(String resourcePath, int cellSize, int directions, int framesPerDir) {
        //  InputStream declared in try so that it can autoclose, without it need to manual in.close();
        try (InputStream in = SpriteSheet.class.getResourceAsStream(resourcePath)) {
            if(in == null) throw new IllegalArgumentException("Sprite not found on classpath: " + resourcePath); 

            BufferedImage sheet = ImageIO.read(in);
            frames = new BufferedImage[directions][framesPerDir]; 
            for (int i = 0; i < directions; i++) {
                for (int j = 0; j < framesPerDir; j++) {
                    frames[i][j] = sheet.getSubimage(
                        j * cellSize, i * cellSize, cellSize, cellSize
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load sprite sheet from: " + resourcePath);
        }
    }

    public BufferedImage get(int dir, int frame) {
        return frames[dir][frame];
    }
}
