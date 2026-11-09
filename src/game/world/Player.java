package game.world;

import game.Input;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Player extends Entity {

    private static final String FRAMES_PATH = "/sprites/player.png";
    
    private NPC talkingTo = null;
    private boolean triggerBattle = false;

    public Player(int tileX, int tileY) {
        super(tileX, tileY, FRAMES_PATH);
    }

    public NPC getTalkingTo() { return talkingTo; }
    public void endTalk() { talkingTo = null; }

    public void update(World world, Input input) {
        boolean wasMoving = moving;
        advanceStep();

        if(wasMoving && !moving) {
            int curr = world.canWalk(tileX, tileY);
            if(curr == 1) { // randomize battle chance
                if(Math.random() < 0.2) triggerBattle = true;
            }  
        } 


        if (!moving) {
            int dx = 0, dy = 0;
            if (input.isDown(KeyEvent.VK_W)) dy = -1;
            else if (input.isDown(KeyEvent.VK_S)) dy = 1;
            else if (input.isDown(KeyEvent.VK_D)) dx = 1;
            else if (input.isDown(KeyEvent.VK_A)) dx = -1;

            if (dx != 0 || dy != 0) tryStep(dx, dy, world);

            // check NPC
            if(input.wasPressed(KeyEvent.VK_E)) {
                NPC npc = world.npcAt(tileX+DX[facing], tileY+DY[facing]);
                // NPC logic nnti
                if(npc != null) talkingTo = npc;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(currentFrame(), pixelX(), pixelY(), null);
    }
}
