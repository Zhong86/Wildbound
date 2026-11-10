package game;

import java.awt.Graphics2D;

// interface gives the methods in this class
//      - implements can also be used with several interfaces
//      - forces the child to write every method
public interface Scene {
    void update(Input input);
    void draw(Graphics2D g);
}
