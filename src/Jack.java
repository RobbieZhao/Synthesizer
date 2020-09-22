import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Jack {
    private Circle circle;

    public Jack() {
        circle = new Circle();
        circle.setRadius(10);
        circle.setFill(Color.BLUE);
    }

    public Circle getWidget() {
        return circle;
    }

    public double getCenterX() {
        return circle.getParent().getLayoutX() + circle.getLayoutX();
    }

    public double getCenterY() {
        return circle.getParent().getLayoutY() + circle.getLayoutY();
    }

    public double distanceToMouse(MouseEvent mouseEvent) {
        double deltaX = mouseEvent.getSceneX() - getCenterX();
        double deltaY = mouseEvent.getSceneY() - getCenterY();

        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }

    public double getRadius() {
        return circle.getRadius();
    }

    public boolean mouseOnJack(MouseEvent mouseEvent) {
        return distanceToMouse(mouseEvent) < getRadius();
    }
}
