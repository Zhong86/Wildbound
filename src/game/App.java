package game;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        // moves from main to the EDT thread
        // Components can be rendered in order, if one on main and the other on EDT, then it can conflict. Swing auto uses EDT, so init on main without moving to EDT can cause races.
        // EDT handles rendering, a slow task will lag it
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Delta");
            GamePanel panel = new GamePanel();

            frame.add(panel); 
            frame.pack();   // size window to fit panel
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);  // center window 
            frame.setVisible(true); // EDT starts running
            
            panel.requestFocusInWindow(); //    active window
            panel.start();
        });
    }
}
