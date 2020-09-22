import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;

public class SquarewaveWidget extends WidgetBase {
    private Slider slider;
    private SquareWave squareWave = new SquareWave(0);

    public SquarewaveWidget() {

        // The SineWave widget has everything in the base widget and a slider to control the frequency
        super("SquareWave");

        setGridPaneDraggable();

        // Create a slider and add it to the GridPane
        slider = new Slider(0, 3000, 0);
        slider.setShowTickLabels(true);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                squareWave.update(new_val.doubleValue());
            }
        });
        gridPane.add(slider, 1, 1, 1, 1);
    }

    public AudioComponent getAudioComponent() {
        return squareWave;
    }

}

