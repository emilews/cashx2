package cashx2.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import cashx2.common.configurations.Version;

public class AppMain extends Application {

    private final int width = 1024;
    private final int height = 650;
    private final Version version = Version.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {

        Label info = new Label("Version: " + version.getAppVersion());
        final Scene scene = new Scene(new StackPane(info),width,height);
        primaryStage.setScene(scene);
        primaryStage.setTitle(version.getAppName());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
