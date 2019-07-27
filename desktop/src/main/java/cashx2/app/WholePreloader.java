package cashx2.app;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import cashx2.common.configurations.AppConfiguration;

public class WholePreloader extends Preloader {
    private Stage preloaderStage;
    private final AppConfiguration appConfiguration = AppConfiguration.getInstance();
    private final int width = appConfiguration.getWidth();
    private final int height = appConfiguration.getHeight();


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage = primaryStage;

        Image logo = new Image(getClass().getResourceAsStream("/images/cashX_logo_V3_big.png"));
        Image icon = new Image(getClass().getResourceAsStream("/images/icon.png"));
        primaryStage.getIcons().add(icon);
        ImageView imageView = new ImageView(logo);
        StackPane sp = new StackPane();
        sp.getChildren().addAll(imageView);
        Scene scene = new Scene(sp, width,height);
        primaryStage.setTitle("CashX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
    @Override
    public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
        if (stateChangeNotification.getType() == StateChangeNotification.Type.BEFORE_START) {
            preloaderStage.hide();
        }
    }
}
