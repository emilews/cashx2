package cashx2.app;


import cashx2.common.configurations.AppConfiguration;
import cashx2.common.configurations.Version;
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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class AppMain extends Application {
    private static AppConfiguration appConfiguration = AppConfiguration.getInstance();
    private final Version version = Version.getInstance();
    private final int width = appConfiguration.getWidth();
    private final int height = appConfiguration.getHeight();


    //For storing purposes
    private final File appDataPath = new File(appConfiguration.getAppdata());
    private final File seedDataPath = new File(appConfiguration.getSeedFileDirectory());
    private final File store = new File(String.valueOf(seedDataPath));

    //List View
    ListView<String> addressesView = new ListView<String>();

    //When there's no seed file, then it's time to create a new one
    private boolean firstTime = false;


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
            appDataPath.mkdirs();
        }
        if(!store.exists()){
            firstTime = true;
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
        if(firstTime){
            Stage popup = new Stage();
            BorderPane borderPane = new BorderPane();
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Text("Creating a new seed"));
            borderPane.setCenter(dialogVbox);
            Scene popupScene = new Scene(borderPane, 600,600);

            popup.getIcons().add(icon);
            popup.setScene(popupScene);
            popup.setTitle("New seed");
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(primaryStage);
            popup.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
