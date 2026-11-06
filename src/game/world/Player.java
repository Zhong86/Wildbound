package game.world;

import game.Input;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Player extends Entity {

    private static final String FRAMES_PATH = "/sprites/player.png";

    public Player(int tileX, int tileY) {
        super(tileX, tileY, FRAMES_PATH);
    }

    public void update(World world, Input input) {
        advanceStep();

        if (!moving) {
            int dx = 0, dy = 0;
            if (input.isDown(KeyEvent.VK_W)) dy = -1;
            else if (input.isDown(KeyEvent.VK_S)) dy = 1;
            else if (input.isDown(KeyEvent.VK_D)) dx = 1;
            else if (input.isDown(KeyEvent.VK_A)) dx = -1;

            if (dx != 0 || dy != 0) tryStep(dx, dy, world);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(currentFrame(), pixelX(), pixelY(), null);
    }
}
