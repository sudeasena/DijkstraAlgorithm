package Demo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import java.util.*;

class Dugum {
    double x,y;
    String name;
    private boolean visited;
    private Circle circle;
    private Text text;
    LinkedList<Kenar> kenarlar;


    Dugum(double x, double y, String name){
        circle = new Circle(10);
        circle.setFill(Color.LIGHTBLUE);
        text = new Text(name);
        this.x=x;
        this.y=y;
        this.name=name;
        visited=false;
        kenarlar = new LinkedList<>();
    }

    Circle getCircle(double scale) {
        circle.setCenterX(x*scale);
        circle.setCenterY(y*scale);
        return circle;
    }

    Circle getCircle() {
        return circle;
    }

    Text getText() {
        //Dugum ismi hizalanıyor.
        text.layoutXProperty().bind(circle.centerXProperty().add(-text.getLayoutBounds().getWidth()/2));
        text.layoutYProperty().bind(circle.centerYProperty().add(5));
        return text;
    }

    boolean isVisited() {
        return visited;
    }

    void visit(){
        visited=true;
    }

    void unvisited(){
        visited=false;
    }




}
