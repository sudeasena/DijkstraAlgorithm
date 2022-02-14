package Demo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

class Kenar {
    Dugum kaynak;
    Dugum hedef;
    double agirlik;
    private Line line;
    private Text text;

    Kenar(Dugum kaynak, Dugum hedef, double agirlik){
        line= new Line();
        text= new Text();
        this.kaynak=kaynak;
        this.hedef=hedef;
        this.agirlik=agirlik;

        DoubleProperty startX = new SimpleDoubleProperty(kaynak.x);
        DoubleProperty startY = new SimpleDoubleProperty(kaynak.y);
        DoubleProperty endX = new SimpleDoubleProperty(hedef.x);
        DoubleProperty endY = new SimpleDoubleProperty(hedef.y);
        line.startXProperty().bind(startX);
        line.startYProperty().bind(startY);
        line.endXProperty().bind(endX);
        line.endYProperty().bind(endY);
        startX.bind(kaynak.getCircle().centerXProperty());
        startY.bind(kaynak.getCircle().centerYProperty());
        endX.bind(hedef.getCircle().centerXProperty());
        endY.bind(hedef.getCircle().centerYProperty());
        text.setText(agirlik+"");
        text.xProperty().bind((startX.add(endX)).divide(2));
        text.yProperty().bind((startY.add(endY)).divide(2));
    }
    Line getLine() {
        return line;
    }

    Text getText() {
        return text;
    }


}
