package game.world;

import java.util.Map;

public class Quest {

    public String title;
    public Map<String, Integer> tasks;
    public String status = "not-taken"; // not-taken, taken, done
    public String reward; // manual parsing nnti

}
