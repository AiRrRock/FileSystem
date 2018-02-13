package is.this_way.better.filesystem.main;

import is.this_way.better.filesystem.gui.controllers.ClosableWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application implements ClosableWindow {
    private Parent root;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InitWindow.fxml"));
        root = loader.load();
        Scene scene = new Scene(root, 250, 200);
        scene.getStylesheets().add(getClass().getResource("/CSS/initTheme.css").toExternalForm());
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
