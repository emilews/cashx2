package cashx2.app;


import cashx2.common.configurations.AppConfiguration;
import cashx2.common.configurations.Version;
import cashx2.common.reader.Reader;
import cashx2.common.writer.Writer;
import cashx2.core.bch.BchWallet;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bitcoinj.crypto.MnemonicCode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppMain extends Application {
    /*TODO: A refactor is needed in order to make this faster-ish for first time boot, moving stage
       and scene creation to init() might solve this.
       I think this approach is not the best. So, for a "mass adoption" (which I really don't see possible,
       because the tech is made for devs/tech savy people), this needs to be quite the "ubiquitous software"
       in which the user gets to do exactly what he wants without thinking (or even know) about, for example,
       what kind of fee or "OP_CODE" to use.
     */
    /*
    Since I am super bored, I'll tell you something about this place: it's hot as fuck!
     */
    private static AppConfiguration appConfiguration = AppConfiguration.getInstance();
    private final Version version = Version.getInstance();
    private final int width = appConfiguration.getWidth();
    private final int height = appConfiguration.getHeight();


    //For storing purposes
    private final File appDataPath = new File(appConfiguration.getAppdata());
    private final File seedDataPath = new File(appConfiguration.getSeedFileDirectory());
    private final File store = new File(String.valueOf(seedDataPath));

    //For wallet
    BchWallet wallet = null;

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
    private TextField passphrase1 = new TextField();{
        passphrase1.setPromptText("Type passphrase...");
    }
    private TextField passphrase2 = new TextField();{
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
            writer = Writer.getInstance();
            writer.changeToSeedSave();
            writer.saveSeed(wordList);
        }else{
            reader = Reader.getInstance();
            wordList.addAll(reader.readSeed());
            System.out.println(wordList);
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
    }

    public void createWallet(){
        String seed = String.join(",", wordList);
        wallet = BchWallet.getInstance(seed, wordList);
    }
    public void saveSeed(){


    }

    public static void main(String[] args) {
        launch(args);
    }
}
