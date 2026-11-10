package game;

import game.world.Entity;
import game.world.World;

public class Camera {

    private int x,y;

    public void follow(int targetX, int targetY, World world) {
        x = targetX + (Entity.TILE  - GamePanel.WIDTH) / 2; 
        y = targetY + (Entity.TILE  - GamePanel.HEIGHT) / 2; 

        // Math.max for top-left boundary
        x = Math.max(0, 
            // Math.min for lower-right boundary
            Math.min(x, world.getPixelWidth() - GamePanel.WIDTH)
        );
        y = Math.max(0, Math.min(y, world.getPixelHeight() - GamePanel.HEIGHT));
    }

    public int getX() { return x; }
    public int getY() { return y; }

}
