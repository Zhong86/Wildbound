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
        this.dialogs = dialog;
        this.quest = newQuest;
    }

    public String getDialog() {
        if(quest.status.equals("not-taken")) return quest.startDialog;
        else if(quest.status.equals("just-finished")) return quest.finishDialog;
        else return dialogs.get(new Random().nextInt(dialogs.size()));
    }

    public String getQuest() {
        return quest.status;
    }

    @Override
    public void draw(Graphics2D g) {

    }
}
