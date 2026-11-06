package game.world;

import game.SpriteSheet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

// abstract blocks: 
//      - cannot be created
//      - can declare methods with no body that subclasses are forced to fill
public abstract class Entity {
    
    public static final int TILE = 16; 
    public static final int DOWN=0, UP=1, LEFT=2, RIGHT=3;

    private final SpriteSheet sprites;

    protected int tileX, tileY; 
    protected int pixelOffsetX, pixelOffsetY;

    protected int facing = DOWN;

    protected boolean moving; 
    protected boolean footToggle;

    protected int moveTicks; 
    protected int stepFrames = 16; //   when = 16, moves 1 tick per frame because 1 tile size is 16

    protected int dirX, dirY;

    protected Entity(int tileX, int tileY, String spritePath) {
        this.tileX = tileX;
        this.tileY = tileY;
        this.sprites = new SpriteSheet(spritePath, TILE, 4, 3); //  4 directions and 3 frames per dir
    }

    //  calculating movement
    public boolean tryStep(int dx, int dy, World world) {
        if(moving) return false; 
        setFacing(dx, dy);
        if(!world.canWalk(tileX+dx, tileY+dy)) return false; 
        dirX=dx; dirY=dy;
        moving=true;moveTicks=0;
        return true;
    }

    protected void advanceStep() {
        if(!moving) return; 
        moveTicks++; 
        pixelOffsetX = dirX * TILE * moveTicks / stepFrames;
        pixelOffsetY = dirY * TILE * moveTicks / stepFrames;

        if(moveTicks >= stepFrames) {
            tileX += dirX; tileY += dirY; 
            pixelOffsetX=0; pixelOffsetY=0;
            moving = false; 
            footToggle = !footToggle;
        }
    }

    protected void setFacing(int dx, int dy) {
        if(dy > 0) facing = DOWN;
        else if(dy < 0) facing = UP; 
        else if(dx > 0) facing = RIGHT; 
        else if(dx < 0) facing = LEFT;
    }


    public int getTileX() { return tileX; }
    public int getTileY() { return tileY; }
    public boolean isMoving() { return moving; }

    protected int pixelX() { return tileX * TILE + pixelOffsetX; }
    protected int pixelY() { return tileY * TILE + pixelOffsetY; }

    //  rendering movement
    protected BufferedImage currentFrame() {
        if(!moving) return sprites.get(facing, 0);
        if(moveTicks < stepFrames / 2) return sprites.get(facing, 0);
        return sprites.get(facing, footToggle ? 1 : 2);
    }

    public abstract void draw(Graphics2D g);
}
