import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePanel extends JPanel {
    
    private static final int FPS = 60;
    public static final int SCALE = 4; 
    public static final int WIDTH=320, HEIGHT=180; 

    private final Input input; 
    private final World world; 
    private final Player player; 

    private final Timer timer; 

    private final int[] START_POS = new int[]{5,5};

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        setBackground(Color.black);

        input = new Input();
        addKeyListener(input); // handle keyboard input with our custom Input

        world = new World();
        player = new Player(START_POS);

        timer = new Timer(1000 / FPS, e -> {
            update(); 
            repaint();
        });
    }

    public void start() {
        timer.start();
    }

    private void update() {

    }

    @Override 
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0); //    runs parent code as well

        Graphics2D g = (Graphics2D) g0.create(); // copy of g0 so scale and rendering hints can be changed without affecting every paint component
        try {
            g.scale(SCALE, SCALE);

            //  specific settings for pixel-art
            //      - interpolation: how to handle pixels when its scaled
            //      - neighbor: enlarged pixel just copy the neighbor
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

            //world.draw(g);
            //player.draw(g);
        } finally {
            g.dispose();
        }
        Toolkit.getDefaultToolkit().sync(); //  forces the painted scene to the screen
    }
}
