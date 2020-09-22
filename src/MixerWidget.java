import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.io.FileNotFoundException;

public class MixerWidget extends WidgetBase{

    private Mixer mixer = new Mixer();
    public MixerWidget() {
        super("Mixer");

        setGridPaneDraggable();

        Jack jack = new Jack();
        jackMap.put("Left", jack);
        gridPane.add(jack.getWidget(), 0, 1, 1, 1);

    }

    public AudioComponent getAudioComponent() {
        return mixer;
    }

}
