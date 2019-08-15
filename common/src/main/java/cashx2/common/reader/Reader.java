package cashx2.common.reader;

import cashx2.common.configurations.AppConfiguration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    BufferedReader bufferedReader = null;
    AppConfiguration appConfiguration;
    private static Reader instance;

    public static synchronized Reader getInstance() {
        if(instance == null){
            instance = new Reader();
        }
        return instance;
    }
    //Only called once when the app starts and it detects that it is not the first time boot
    public List<String> readSeed() throws IOException {
        appConfiguration = AppConfiguration.getInstance();
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(appConfiguration.getSeedFileDirectory())));
        String[] line = bufferedReader.readLine().split(",");
        List<String> wordList = new ArrayList<String>();
        for(int i = 0; i < 21; i++){
            wordList.add(i, line[i]);
        }
        return wordList;
    }
}
