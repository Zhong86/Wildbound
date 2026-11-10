package game.world;

import java.util.Map;

public class Quest {

    protected String title;

    protected Map<String, Integer> tasks;
    protected String status = "not-taken"; // not-taken, taken, just-finished, done
    protected String reward; // manual parsing nnti

    protected String startDialog;
    protected String finishDialog;

    public Quest(String title, Map<String, Integer> tasks, String status, String reward, String startDialog,
            String finishDialog) {
        this.title = title;
        this.tasks = tasks;
        this.status = status;
        this.reward = reward;
        this.startDialog = startDialog;
        this.finishDialog = finishDialog;
    }

    private void loadQuest(String path) {
        
    }
}
