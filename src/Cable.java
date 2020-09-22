import javafx.scene.shape.Line;

public class Cable {
    private Line line;
    private Base startWidget;
    private Base endWidget;

    public Cable(double x, double y, Base startWidget) {
        line = new Line();
        line.setStartX(x);
        line.setStartY(y);
        this.startWidget = startWidget;
    }

    public void setEndPoint(double x, double y, Base endWidget) {
        line.setEndX(x);
        line.setEndY(y);
        this.endWidget = endWidget;
        connect();
    }


    public Line getLine() {
        return line;
    }

    public void connect() {

        endWidget.getAudioComponent().connectInput(startWidget.getAudioComponent());
    }
}
