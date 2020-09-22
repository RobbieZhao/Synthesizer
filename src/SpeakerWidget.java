import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.util.HashMap;
import java.util.Map;

public class SpeakerWidget implements Base{
    private GridPane gridPane = new GridPane();
    private Mixer mixer = new Mixer();
    protected Map<String, Jack> jackMap = new HashMap<>();


    public SpeakerWidget() {
        Label label = new Label("Speaker");
        gridPane.add(label, 0, 0, 1, 1);

        Jack jack = new Jack();
        jackMap.put("Middle", jack);
        gridPane.add(jack.getWidget(), 0, 1, 1, 1);
        GridPane.setHalignment(jack.getWidget(), HPos.CENTER);
    }

    public Map.Entry<String, Jack> mouseOnJacks(MouseEvent mouseEvent) {
        for (Map.Entry<String,Jack> entry : jackMap.entrySet()) {
            if (entry.getValue().mouseOnJack(mouseEvent)) {
                return entry;
            }
        }
        return null;
    }

    public void setPosition(int X, int Y) {
        gridPane.setLayoutX(X);
        gridPane.setLayoutY(Y);
    }

    public GridPane getWidget() {
        return gridPane;
    }

    public AudioComponent getAudioComponent() {
        return mixer;
    }

    public void speak() throws LineUnavailableException {
        AudioClip audioClip = mixer.getClip();

        Clip c = AudioSystem.getClip(); //terrible name

        AudioFormat format16 = new AudioFormat(AudioClip.getSampleRate(), 16, 1, true, false);

        c.open(format16, audioClip.getData(), 0, audioClip.getData().length); //reads data from my byte array to play it

        c.start();
        c.loop(1);
    }
}
