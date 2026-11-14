package game.world;

import java.awt.Graphics2D;

import game.Camera;
import game.GamePanel;
import game.Input;
import game.Scene;

public class OverworldScene implements Scene {

    private final GamePanel game;

    private final World world = new World();
    private final Player player = new Player(world.getStartX(), world.getStartY());
    private final Camera camera = new Camera();

    public OverworldScene(GamePanel game) {
        this.game = game;
    }

    @Override
    public void update(Input input) {
        player.update(world, input);
        camera.follow(player.pixelX(), player.pixelY(), world);
    }

    @Override
    public void draw(Graphics2D g) {
        Graphics2D worldG = (Graphics2D) g.create();
        worldG.translate(-camera.getX(), -camera.getY());
        world.draw(worldG);
        player.draw(worldG); 
        worldG.dispose();
    }
}
