package cashx2.app;


import cashx2.core.bch.BchWallet;
import com.softwareverde.bitcoin.address.AddressInflater;
import com.softwareverde.bitcoin.secp256k1.key.PrivateKey;
import com.softwareverde.constable.bytearray.ByteArray;
import com.softwareverde.util.HexUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import cashx2.common.configurations.Version;
import cashx2.common.configurations.AppConfiguration;

import java.io.*;

public class AppMain extends Application {
    private static AppConfiguration appConfiguration = AppConfiguration.getInstance();
    private final Version version = Version.getInstance();
    private final int width = appConfiguration.getWidth();
    private final int height = appConfiguration.getHeight();
    //Wallet
    public static BchWallet wallet;
    //Menu booleans
    private static boolean isInBCash = true;
    private static boolean isInSlp = false;


    //For storing purposes
    private final File appDataPath = new File(appConfiguration.getAppdata());
    String storePath = appConfiguration.getAppdata() + "\\stored.csi";
    private static PrivateKey privateKey;

    //For testing purposes
    private static String address;

    //List View
    ListView<String> addressesView = new ListView<String>();


    //Menu bars and views
    Menu Home = new Menu("Home");
    Menu Settings = new Menu("Settings");
    MenuBar menuBar = new MenuBar();
    VBox vBoxMenuBar = new VBox(menuBar);
    VBox vBoxAddressList = new VBox(addressesView);

    //Root Pane
    BorderPane root = new BorderPane();
    //Addresses pane
    StackPane addressPane = new StackPane();
    //Images
    Image icon = new Image(getClass().getResourceAsStream("/images/icon.png"));

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

            wallet = new BchWallet();
            wallet.addPrivateKey(privateKey);
            ObservableList<String> addresses = FXCollections.observableArrayList(
                    wallet.getReceivingAddress().toBase58CheckEncoded());
            addressesView.setItems(addresses);
            addressesView.setPrefWidth(500);
            addressesView.setPrefHeight(400);
        }
        //We add the menu and construct the panes
        menuBar.getMenus().addAll(Home, Settings);
        addressPane.setPadding(new Insets(20));
        addressPane.getChildren().addAll(vBoxAddressList);
        root.setTop(vBoxMenuBar);
        root.setLeft(addressPane);



    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //App icon
        primaryStage.getIcons().add(icon);
        Scene mainScene = new Scene(root,width,height);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle(version.getAppName());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
