import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

abstract public class WidgetBase implements Base{

    protected GridPane gridPane = new GridPane();
    protected Map<String, Jack> jackMap = new HashMap<>();
    protected Map<String, ArrayList<Line>> connectedLinesMap = new HashMap<>();


    protected WidgetBase(String text) {

        setGridPaneStyle();

        Label label = new Label(text);
        GridPane.setHalignment(label, HPos.CENTER);
        gridPane.add(label, 1, 0, 1, 1);

        Jack jack = new Jack();
        jackMap.put("Right", jack);
        gridPane.add(jack.getWidget(), 2, 1, 1, 1);

        connectedLinesMap.put("linesStartWithWidget", new ArrayList<>());
        connectedLinesMap.put("linesEndWithWidget", new ArrayList<>());

    }

    public void setGridPaneStyle() {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setMinWidth(200);
        gridPane.setMinHeight(80);
        gridPane.setHgap(30);
        gridPane.setVgap(10);
        gridPane.setStyle(
                "-fx-padding: 10;" +
                        "-fx-border-style: solid inside;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-insets: 5;" +
                        "-fx-border-radius: 5;" +
                        "-fx-border-color: blue;");
    }

    public GridPane getWidget() {
        return gridPane;
    }

    public abstract AudioComponent getAudioComponent();

    public Map.Entry<String, Jack> mouseOnJacks(MouseEvent mouseEvent) {
        for (Map.Entry<String,Jack> entry : jackMap.entrySet()) {
            if (entry.getValue().mouseOnJack(mouseEvent)) {
                return entry;
            }
        }
        return null;
    }

    public void addLines(Line line, boolean StartWithWidget) {
        if (StartWithWidget) {
            connectedLinesMap.get("linesStartWithWidget").add(line);
        } else {
            connectedLinesMap.get("linesEndWithWidget").add(line);
        }
    }

    public void setGridPaneDraggable() {
        class Delta { double x, y; }
        final Delta dragDelta = new Delta();

        class Position { boolean isMouseOnJacks;}
        final Position position = new Position();

        gridPane.setOnMousePressed(e -> {
            if (mouseOnJacks(e) == null) {
                position.isMouseOnJacks = false;
                dragDelta.x = e.getSceneX() - gridPane.getLayoutX();
                dragDelta.y = e.getSceneY() - gridPane.getLayoutY();
                gridPane.setCursor(Cursor.HAND);
            } else {
                position.isMouseOnJacks = true;
            }
        });

        gridPane.setOnMouseDragged(e -> {
            if (!position.isMouseOnJacks) {

                double X = gridPane.getLayoutX();
                double Y = gridPane.getLayoutY();
                gridPane.setLayoutX(e.getSceneX() - dragDelta.x);
                gridPane.setLayoutY(e.getSceneY() - dragDelta.y);

                ArrayList<Line> linesStartWithWidget = connectedLinesMap.get("linesStartWithWidget");
                for (Line line : linesStartWithWidget) {
                    line.setStartX(line.getStartX() + gridPane.getLayoutX() - X);
                    line.setStartY(line.getStartY() + gridPane.getLayoutY() - Y);
                }

                ArrayList<Line> linesEndWithWidget = connectedLinesMap.get("linesEndWithWidget");
                for (Line line : linesEndWithWidget) {
                    line.setEndX(line.getEndX() + gridPane.getLayoutX() - X);
                    line.setEndY(line.getEndY() + gridPane.getLayoutY() - Y);
                }
            }
        });

        gridPane.setOnMouseReleased(e -> gridPane.setCursor(Cursor.MOVE));
    }

    public void removeLine() {

        ArrayList<Line> linesStartWithWidget = connectedLinesMap.get("linesStartWithWidget");
        linesStartWithWidget.remove(linesStartWithWidget.size() - 1);

    }
}
