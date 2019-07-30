package cashx2.app;


import cashx2.common.security.Hash;
import com.softwareverde.bitcoin.address.AddressInflater;
import com.softwareverde.bitcoin.secp256k1.key.PrivateKey;
import com.softwareverde.constable.bytearray.ByteArray;
import com.softwareverde.util.HexUtil;
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

import java.io.*;

public class AppMain extends Application {
    private static AppConfiguration appConfiguration = AppConfiguration.getInstance();
    private final Version version = Version.getInstance();
    private final int width = appConfiguration.getWidth();
    private final int height = appConfiguration.getHeight();


    //For storing purposes
    private final File appDataPath = new File(appConfiguration.getAppdata());
    String storePath = appConfiguration.getAppdata() + "\\stored.csi";
    private static PrivateKey privateKey;

    //For testing purposes
    private static String address;

    @Override
    public  void init() throws Exception{
        //Start reading from memory
        if(!appDataPath.exists()){
            File store = new File(storePath);
            appDataPath.mkdirs();
            if(store.createNewFile()){
                System.out.println("Created new store!");
            }
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(store));
            privateKey = PrivateKey.createNewKey();
            bufferedOutputStream.write(privateKey.getBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        }
        if(privateKey != null){
            AddressInflater addressInflater = new AddressInflater();
            address = addressInflater.fromPrivateKey(privateKey).toBase58CheckEncoded();
        }else{
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(storePath));
            int character;
            StringBuilder pK = new StringBuilder("");
            while((character = bufferedInputStream.read()) != -1){
                pK.append((char)character);
            }
            bufferedInputStream.close();
            String hex = HexUtil.toHexString(pK.toString().getBytes());
            ByteArray byteArray = ByteArray.fromHexString(hex);
            privateKey = PrivateKey.fromBytes(byteArray);

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image icon = new Image(getClass().getResourceAsStream("/images/icon.png"));
        primaryStage.getIcons().add(icon);
        Label info = new Label(address);
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
