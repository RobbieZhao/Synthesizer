import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

import java.util.HashMap;
import java.util.Map;

public interface Base {

    AudioComponent getAudioComponent();

    default void addLines(Line line, boolean StartWithWidget) {}

    default void removeLine() {}

    Map.Entry<String, Jack> mouseOnJacks(MouseEvent mouseEvent);

}