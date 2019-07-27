package cashx2.app;


import com.softwareverde.bitcoin.address.AddressInflater;
import com.softwareverde.bitcoin.secp256k1.key.PrivateKey;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import cashx2.common.configurations.Version;
import cashx2.common.configurations.AppConfiguration;

import com.softwareverde.bitcoin.wallet.Wallet;
import com.softwareverde.bitcoin.chain.time.MutableMedianBlockTime;

public class AppMain extends Application {
    private static AppConfiguration appConfiguration = AppConfiguration.getInstance();
    private final Version version = Version.getInstance();
    private final int width = appConfiguration.getWidth();
    private final int height = appConfiguration.getHeight();
    private String receivingAddress;

    @Override
    public  void init() throws Exception{
        //Start reading from memory
        String appdata = appConfiguration.getAppdata();
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image icon = new Image(getClass().getResourceAsStream("/images/icon.png"));
        primaryStage.getIcons().add(icon);
        Label info = new Label(receivingAddress);
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
