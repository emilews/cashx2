package cashx2.common.reader;

import cashx2.common.configurations.AppConfiguration;

import java.io.*;
import java.util.List;

public class Reader {
    BufferedReader bufferedReader = null;
    AppConfiguration appConfiguration;
    private Reader instance;

    public synchronized Reader getInstance() {
        if(instance == null){
            instance = new Reader();
        }
        return instance;
    }
    //Only called once when the app starts and it detects that it is not the first time
    public List<String> readSeed() throws IOException {
        appConfiguration = AppConfiguration.getInstance();
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(appConfiguration.getSeedFileDirectory())));
        String[] line = bufferedReader.readLine().split(",");
        List<String> wordList = null;
        for(String s : line){
            wordList.add(s);
        }
        return wordList;
    }
}
