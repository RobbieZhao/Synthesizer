import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;


public class SinewaveWidget extends WidgetBase {
    private Slider slider;
    private SineWave sineWave = new SineWave(0);

    public SinewaveWidget() {

        // The SineWave widget has everything in the base widget and a slider to control the frequency
        super("SineWave");

        setGridPaneDraggable();

        // Create a slider and add it to the GridPane
        slider = new Slider(0, 1000, 0);
        slider.setShowTickLabels(true);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                sineWave.update(new_val.doubleValue());
            }
        });
        gridPane.add(slider, 1, 1, 1, 1);
    }

    public AudioComponent getAudioComponent() {
        return sineWave;
    }

}
