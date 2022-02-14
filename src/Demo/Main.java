package Demo;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Gui gui = new Gui();
        Pane pane = new Pane();

        MenuBar mb = new MenuBar();
        mb.setPrefWidth(2160);

        GridPane girisEkraniUI = gui.arayüzOlusturma();

        Menu menu = new Menu("Graf");
        MenuItem m1 = new MenuItem("Dijkstra");
        MenuItem m2 = new MenuItem("Tabu Arama Algoritması");

        menu.getItems().addAll(m1,m2);

        mb.getMenus().add(menu);

        VBox vb = new VBox(mb);

        EventHandler<ActionEvent> event = e -> {
            Pane dijkstraUI = new Pane();

            if (e.getSource() == m1) {
                gui.arayuz(dijkstraUI);
            }

            Group group = new Group();
            group.getChildren().addAll(dijkstraUI, vb);

            primaryStage.setTitle("ALGORİTMALAR");
            primaryStage.setScene(new Scene(group, 620, 500));
            primaryStage.show();

        };

        m1.setOnAction(event);
        pane.getChildren().addAll(girisEkraniUI, vb);
        primaryStage.setTitle("ALGORİTMALAR");
        primaryStage.setScene(new Scene(pane, 720, 480));
        primaryStage.show();
    }
        public static void main(String[] args){
            launch(args);
        }
}
