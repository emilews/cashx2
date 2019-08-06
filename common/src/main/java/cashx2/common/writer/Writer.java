package cashx2.common.writer;

import cashx2.common.configurations.AppConfiguration;

import java.io.*;
import java.util.List;

public class Writer {
    //Anti stupid protection for each type of save
    private boolean antiStupidProtectionForSeed = false;

    private BufferedWriter bufferedWriter = null;
    private AppConfiguration appConfiguration;
    private static Writer instance;

    public static synchronized Writer getInstance(){
        if(instance == null){
            instance = new Writer();
        }
        return instance;
    }
    //Methods for changing what will be saved
    //Only used for first time
    public void changeToSeedSave() throws FileNotFoundException {
        antiStupidProtectionForSeed = true;
        appConfiguration = AppConfiguration.getInstance();
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(appConfiguration.getSeedFileDirectory())));
    }


    //Save data depending on bufferedWriter config
    public boolean saveSeed(List<String> wordList) throws IOException {
        //Yes, it is a secure method for ensuring it only works when it is locked (I mean, when the protection is true)
        if(antiStupidProtectionForSeed){
            for(int i = 0; i < wordList.size(); i++){
                //When we reach the last word, we only add the word, otherwise we add a coma to it
                if (i == wordList.size()-1){
                    bufferedWriter.write(wordList.get(i));
                }else{
                    bufferedWriter.write(wordList.get(i) + ",");
                }
            }
            //Flush, close and nullify the object for a new use
            bufferedWriter.flush();
            bufferedWriter.close();
            bufferedWriter = null;
            return true;
        }
        return false;
    }

}
