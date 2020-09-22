import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javax.sound.sampled.LineUnavailableException;
import java.util.ArrayList;
import java.util.Map;


public class MyApp extends Application {

    // Parameters for layout
    private final int sceneWidth = 1200;
    private final int sceneHeight = 800;
    private int buttonWidth = 150;
    private final int paneWidth = sceneWidth - buttonWidth;

    private ArrayList<Cable> allCables = new ArrayList<>();
    private ArrayList<Base> widgets = new ArrayList<>();

    private boolean draw = false;
    private Base startWidget;
    private String startJackSide;

    private void setPaneMouseEvents(Pane pane) {
        pane.setOnMousePressed(e -> {
            // See if the mouse is on one of the widgets
            for (Base widget : widgets) {
                Map.Entry<String, Jack> tmpEntry = widget.mouseOnJacks(e);
                if (tmpEntry != null) {
                    draw = true;
                    startJackSide = tmpEntry.getKey();
                    Cable cable = new Cable(tmpEntry.getValue().getCenterX(), tmpEntry.getValue().getCenterY(), widget);
                    allCables.add(cable);
                    widget.addLines(cable.getLine(), true);
                    startWidget = widget;
                    break;
                }
            }
        });

        pane.setOnMouseReleased(e -> {
            if (draw) {
                for (Base widget : widgets) {
                    if (widget == startWidget) { // If the mouse is at the start widget
                        continue;
                    }
                    Map.Entry<String, Jack> tmpEntry = widget.mouseOnJacks(e);
                    if (tmpEntry != null && !tmpEntry.getKey().equals(startJackSide)) {
                        Cable lastAddedCable = allCables.get(allCables.size() - 1);
                        lastAddedCable.setEndPoint(tmpEntry.getValue().getCenterX(), tmpEntry.getValue().getCenterY(), widget);
                        widget.addLines(lastAddedCable.getLine(), false);
                        draw = false;
                        break;
                    }
                }
                // if the mouse is not on either of the jack, delete the line
                if (draw) {
                    Line tmpLine = allCables.get(allCables.size() - 1).getLine();
                    pane.getChildren().remove(tmpLine);
                    allCables.set(allCables.size() - 1, null);
                    startWidget.removeLine();
                    draw = false;
                }
            }
        });

        pane.setOnMouseDragged(e -> {
            if (draw) {
                Line tmpLine = allCables.get(allCables.size() - 1).getLine();
                tmpLine.setEndX(e.getSceneX());
                tmpLine.setEndY(e.getSceneY());
                if (!pane.getChildren().contains(tmpLine)) {
                    pane.getChildren().add(tmpLine);
                }
            }
        });
    }


    @Override
    public void start(Stage primaryStage) {

        Pane pane = new Pane();
        pane.setMinWidth(paneWidth);
        pane.setMaxWidth(paneWidth);

        setPaneMouseEvents(pane);

        SpeakerWidget speakerWidget = new SpeakerWidget();
        speakerWidget.setPosition(paneWidth - 100, sceneHeight - 100);
        pane.getChildren().add(speakerWidget.getWidget());
        widgets.add(speakerWidget);

        Button playButton = new Button("Play");
        Button volumeControlButton = new Button("VolumeControl");
        Button sineWaveButton = new Button("SineWave");
        Button mixerButton = new Button("Mixer");
        Button squareWaveButton = new Button("SquareWave");

        playButton.setOnAction(e -> {
            try {
                speakerWidget.speak();
            } catch (LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        volumeControlButton.setOnAction(e -> {
            VolumeControl vc = new VolumeControl();
            pane.getChildren().add(vc.getWidget());
            widgets.add(vc);
        });

        sineWaveButton.setOnAction(e -> {
            SinewaveWidget sw = new SinewaveWidget();
            pane.getChildren().add(sw.getWidget());
            widgets.add(sw);
        });

        mixerButton.setOnAction(e -> {
            MixerWidget mw = new MixerWidget();
            pane.getChildren().add(mw.getWidget());
            widgets.add(mw);
        });

        squareWaveButton.setOnAction(e -> {
            SquarewaveWidget sw1 = new SquarewaveWidget();
            pane.getChildren().add(sw1.getWidget());
            widgets.add(sw1);
        });


        playButton.setMaxWidth(buttonWidth);
        volumeControlButton.setMaxWidth(buttonWidth);
        sineWaveButton.setMaxWidth(buttonWidth);
        mixerButton.setMaxWidth(buttonWidth);
        squareWaveButton.setMaxWidth(buttonWidth);


        VBox vBox = new VBox(20, playButton, volumeControlButton, sineWaveButton, mixerButton, squareWaveButton);
        HBox hBox = new HBox(10, pane, vBox);

        Scene scene = new Scene(hBox, sceneWidth, sceneHeight);
        Group group = new Group();

        primaryStage.setTitle("Synthesizer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}