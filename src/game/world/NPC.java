package game.world;

import java.awt.Graphics2D;
import java.util.List;
import java.util.Random;

public class NPC extends Entity {

    protected List<String> dialogs;
    protected Quest quest;

    public NPC(int tileX, int tileY, String framesPath, 
        List<String> dialog, Quest newQuest
    ) {
        super(tileX, tileY, framesPath);
        dialogs = dialog;
        quest = newQuest;
    }

    public String getDialog() {
        return dialogs.get(new Random().nextInt(dialogs.size()));
    }

    public String getQuest() {
        return quest.status;
    }

    @Override
    public void draw(Graphics2D g) {

    }
}
