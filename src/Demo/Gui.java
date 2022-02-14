package Demo;
    import javafx.geometry.*;
    import javafx.scene.control.*;
    import javafx.scene.layout.*;

public class Gui {
    private Graf graf=new Graf();
    private GrafOlustur grafOlustur=new GrafOlustur(graf);

    GridPane arayüzOlusturma(){
        GridPane gridPane=new GridPane();

        return gridPane;
    }

    void arayuz(Pane pane){
        grafOlustur.grafıGoster(pane);
        Label kaynakLabel=new Label("KAYNAK :");
        kaynakLabel.setLayoutX(230);
        kaynakLabel.setLayoutY(30);
        TextField kaynakField=new TextField();
        kaynakField.setPromptText("");
        kaynakField.setPrefHeight(10);
        kaynakField.setPrefWidth(30);
        kaynakField.setLayoutX(285);
        kaynakField.setLayoutY(30);
        pane.getChildren().add(kaynakLabel);
        pane.getChildren().add(kaynakField);

        Label hedefLabel=new Label("HEDEF :");
        hedefLabel.setLayoutY(30);
        hedefLabel.setLayoutX(350);
        TextField hedefField=new TextField();
        hedefField.setPromptText("");
        hedefField.setPrefHeight(10);
        hedefField.setPrefWidth(30);
        hedefField.setLayoutX(395);
        hedefField.setLayoutY(30);
        pane.getChildren().addAll(hedefLabel,hedefField);


        Button DijkstraButton=new Button("DIJKSTRA");
        DijkstraButton.setPrefHeight(50);
        DijkstraButton.setDefaultButton(true);
        DijkstraButton.setPrefWidth(100);
        DijkstraButton.setLayoutX(500);
        DijkstraButton.setLayoutY(30);
        pane.getChildren().add(DijkstraButton);

        DijkstraButton.setOnAction(event -> grafOlustur.animasyonYolu(pane,kaynakField.getText(),hedefField.getText(),5));

        grafOlustur.grafEkrani(pane,5,false);


    }




}
