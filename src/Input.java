import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Input extends KeyAdapter {
    // defines Set as Concurrent so it can: 
    //      1. handle multiple key inputs at the same time
    //      2. have consistent smooth movement
    //      3. game loop controls input, not OS key rules
    private final Set<Integer> held = ConcurrentHashMap.newKeySet();

    @Override public void keyPressed(KeyEvent e) {
        held.add(e.getKeyCode());
    }

    @Override public void keyReleased(KeyEvent e) {
        held.remove(e.getKeyCode());
    }

    public boolean isDown(int keyCode) {
        return held.contains(keyCode);
    }
}
