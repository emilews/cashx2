package cashx2.app;


import cashx2.common.configurations.AppConfiguration;
import cashx2.common.configurations.Version;
import cashx2.common.reader.Reader;
import cashx2.common.writer.Writer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bitcoinj.crypto.MnemonicCode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppMain extends Application {
    private static AppConfiguration appConfiguration = AppConfiguration.getInstance();
    private final Version version = Version.getInstance();
    private final int width = appConfiguration.getWidth();
    private final int height = appConfiguration.getHeight();


    //For storing purposes
    private final File appDataPath = new File(appConfiguration.getAppdata());
    private final File seedDataPath = new File(appConfiguration.getSeedFileDirectory());
    private final File store = new File(String.valueOf(seedDataPath));

    //For first time boot
    private MnemonicCode mnemonicCode = null;
    {
        try {
            mnemonicCode = new MnemonicCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private List<String> fullWordList = null;
    private List<String> wordList = new ArrayList<String>();
    private Writer writer = null;
    private Reader reader = null;


    //List View
    private ListView<String> addressesView = new ListView<String>();
    //Seed view
    private TextArea seedArea = new TextArea();{
        seedArea.setEditable(false);
        seedArea.setPrefWidth(300);
    }
    //Passphrase view from user
    private TextArea passphrase1 = new TextArea();{
        passphrase1.setPrefColumnCount(20);
        passphrase1.setPrefRowCount(20);
        passphrase1.setPromptText("Type passphrase...");
    }
    private TextArea passphrase2 = new TextArea();{
        passphrase2.setPrefColumnCount(20);
        passphrase2.setPrefRowCount(20);
        passphrase2.setPromptText("Retype passphrase...");
    }

    //Button for first time


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
            fullWordList = mnemonicCode.getWordList();
            int rand = 0;
            for(int i = 0; i <= 20; i++){
                rand = (int)(Math.random()*2048);
                String s = null;
                if (rand == 0){
                    s = fullWordList.get(rand);
                }else{
                    s = fullWordList.get(rand-1);
                }
                wordList.add(s);
            }
            firstTime = true;
        }


        for(int i = 0; i < wordList.size(); i++){
            if(i == wordList.size()-1){
                seedArea.appendText(wordList.get(i));
            }else{
                seedArea.appendText(wordList.get(i) + " ");
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
        if(firstTime){
            Stage popup = new Stage();
            BorderPane borderPane = new BorderPane();
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Text("Creating a new seed"));
            borderPane.setTop(dialogVbox);
            borderPane.setCenter(seedArea);
            BorderPane passPane = new BorderPane();
            passPane.setPrefWidth(50);
            passPane.setCenter(passphrase1);
            passPane.setBottom(passphrase2);
            passPane.setPrefHeight(40);
            borderPane.setBottom(passPane);
            Scene popupScene = new Scene(borderPane, 800,600);

            popup.getIcons().add(icon);
            popup.setScene(popupScene);
            popup.setTitle("New seed");
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(primaryStage);
            popup.show();
        }
    }

    public void createDeterministicSeed(){

    }
    public void saveSeed(){


    }

    public static void main(String[] args) {
        launch(args);
    }
}
