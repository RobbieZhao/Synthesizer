import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;


public class VolumeControl extends WidgetBase {
    private Slider slider;
    private Filter filter = new Filter();

    public VolumeControl() {

        // The VolumeControl widget has everything in base widget, a slider and a jack on the left
        super("Volume");

        setGridPaneDraggable();

        slider = new Slider(0, 1, 0);
        slider.setShowTickLabels(true);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                filter.setScale(new_val.doubleValue());
            }
        });
        gridPane.add(slider, 1, 1, 1, 1);

        Jack jack = new Jack();
        jackMap.put("Left", jack);
        gridPane.add(jack.getWidget(), 0, 1, 1, 1);

    }

    public AudioComponent getAudioComponent() {
        return filter;
    }
}


