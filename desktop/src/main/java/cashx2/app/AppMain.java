package cashx2.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import cashx2.common.configurations.Version;
import cashx2.common.configurations.AppConfiguration;
public class AppMain extends Application {
    private static AppConfiguration appConfiguration = AppConfiguration.getInstance();
    private final Version version = Version.getInstance();
    private final int width = appConfiguration.getWidth();
    private final int height = appConfiguration.getHeight();

    @Override
    public  void init() throws Exception{
        Thread.sleep(5000);
    }

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
