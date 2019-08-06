package cashx2.app;


import cashx2.common.configurations.AppConfiguration;
import cashx2.common.configurations.Version;
import cashx2.core.bch.BchWallet;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

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
    private final File seedDataPath = new File(appConfiguration.getSeedFileDirectory());


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
            File store = new File(String.valueOf(seedDataPath));
            appDataPath.mkdirs();
            if(store.createNewFile()){
                System.out.println("Created new store!");
            }
        }
        addressesView.setPrefWidth(500);
        addressesView.setPrefHeight(400);
        
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
