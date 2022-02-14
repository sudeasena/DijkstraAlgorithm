package Demo;

import javafx.animation.PathTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.*;

import java.util.ArrayList;
import java.util.Stack;


class GrafOlustur {
    private Graf graf;
    private ToggleButton dugumEkleButon=new ToggleButton("DÜĞÜM EKLE");
    private ToggleButton kenarEkleButon=new ToggleButton("KENAR EKLE");
    private DoubleProperty endX;
    private DoubleProperty endY;
    private Line yeniKenar;

    private Slider slider=new Slider();
    private HBox buttonBox=new HBox();

    GrafOlustur(Graf graf){
        this.graf=graf;
        ToggleGroup toggleButtons = new ToggleGroup();
        toggleButtons.getToggles().addAll(dugumEkleButon,kenarEkleButon);
        buttonBox.getChildren().addAll(dugumEkleButon,kenarEkleButon);
    }

    void grafıGoster(Pane pane){
        slider.setValue(5);
        buttonBox.setSpacing(10);
        buttonBox.setLayoutX(20);
        buttonBox.setLayoutY(30);

        Pane tempPane=new Pane();
        tempPane.setPrefWidth(2160);
        tempPane.setPrefHeight(1080);
        grafEkrani(tempPane,5,true);


        TextInputDialog td=new TextInputDialog("");
        td.setHeaderText("oluşturulan düğümü ismini giriniz:");

        tempPane.setOnMouseClicked(mouseEvent -> {
            if(dugumEkleButon.isSelected()){
                td.showAndWait();
                tempPane.getChildren().clear();
                graf.dugumEkle(mouseEvent.getX()/slider.getValue(),mouseEvent.getY()/slider.getValue(),td.getEditor().getText());
                grafEkrani(tempPane,slider.getValue(),true);
            }

        });

        pane.getChildren().add(tempPane);
        pane.getChildren().add(buttonBox);

    }

    private void kenarCizdir(Pane tempPane, final Circle circle,Dugum dugum, double scale){


        final String[] kaynak=new String[1];

        //fareye basıldığından yapılacak işlemler
        circle.setOnMousePressed(mouseEvent -> {
            if(kenarEkleButon.isSelected()){
                kaynak[0]=dugum.name;
                yeniKenar=new Line();
                yeniKenar.setStartX(mouseEvent.getX());
                yeniKenar.setStartY(mouseEvent.getY());
                endX=new SimpleDoubleProperty(mouseEvent.getX());
                endY=new SimpleDoubleProperty(mouseEvent.getY());
                yeniKenar.endXProperty().bind(endX);
                yeniKenar.endYProperty().bind(endY);
                tempPane.getChildren().add(yeniKenar);

            }
        });

        //fare serbest bırakıldıgında yapılacak işlemler
        circle.setOnMouseReleased(mouseEvent -> {
            if(kenarEkleButon.isSelected()){
                endX.unbind();
                endY.unbind();
                TextInputDialog td= new TextInputDialog("");
                for(Dugum i:graf.getDugumler()){
                    if(icerdeMi(mouseEvent.getX(),mouseEvent.getY(),i,scale)){
                        td.setHeaderText("oluşturulan kenarın ağırlığını giriniz:");
                        td.showAndWait();
                        double agirlik=Double.parseDouble(td.getEditor().getText());
                        graf.kenarEkle(kaynak[0],i.name,agirlik);
                    }
                }

                tempPane.getChildren().clear();
                grafEkrani(tempPane,scale,true);

            }
        });

        //kenarı sürükleme
        circle.setOnMouseDragged(mouseEvent -> {
            if(kenarEkleButon.isSelected()){
                endX.set(mouseEvent.getX());
                endY.set(mouseEvent.getY());
            }


        });

        //kenarın başladıgı noktayı alır.
        circle.setOnMouseEntered(mouseEvent -> {
            if(!mouseEvent.isPrimaryButtonDown()){
                circle.getScene().setCursor(Cursor.HAND);
            }
        });

        //kenarın bittiği noktayı alır.
        circle.setOnMouseExited(mouseEvent -> {
            if(!mouseEvent.isPrimaryButtonDown()){
                circle.getScene().setCursor(Cursor.DEFAULT);
            }
        });

    }

    private boolean icerdeMi(double x1, double y1, Dugum dugum, double scale) {
        double mesafe;
        //iki nokta arası uzaklık formülü
        mesafe=Math.sqrt((x1-(dugum.x*scale))*(x1-(dugum.x*scale))+(y1-(dugum.y*scale))*(y1-(dugum.y*scale)));
        return mesafe<10;
    }

    //Düğümleri ekrana çizdirme
    void grafEkrani(Pane pane,double scale,boolean kenarCizdir){
        ArrayList<Kenar> kenarArrayList=new ArrayList<>();
        graf.getDijkstra().dugumKenarEkleme(kenarArrayList);

        for(Kenar i: kenarArrayList){
            pane.getChildren().addAll(i.getLine(),i.getText());
        }
        for(int i=0;i<graf.getDugumler().size();i++){
            pane.getChildren().add(graf.getDugumler().get(i).getCircle(scale));
            pane.getChildren().add(graf.getDugumler().get(i).getText());

            if(kenarCizdir){
                kenarCizdir(pane,graf.getDugumler().get(i).getCircle(scale),graf.getDugumler().get(i),scale);
            }
        }


    }

    //Dijkstra kırmızı circle hareket ediyor.
    void animasyonYolu(Pane pane,String kaynak,String hedef,double scale){
        Dugum kaynakDugum=graf.dugumGetir(kaynak);
        Path yol=new Path();
        yol.getElements().add(new MoveTo(kaynakDugum.x*scale,kaynakDugum.y*scale));
        Stack<Dugum> dugumStack=graf.DugumYolu(kaynak,hedef);

            while(!dugumStack.isEmpty()){
                Dugum dugum=dugumStack.pop();
                yol.getElements().add(new LineTo(dugum.x*scale,dugum.y*scale));
            }
            Shape shape=new Circle(kaynakDugum.x*scale,kaynakDugum.y*scale,6,Color.RED);
            PathTransition pathTransition = new PathTransition();

        pane.getChildren().add(shape);
        pathTransition.setNode(shape);

            pathTransition.setDuration(Duration.seconds(2));
            pathTransition.setPath(yol);
            pathTransition.setCycleCount(PathTransition.INDEFINITE);
            pathTransition.play();

    }

}
