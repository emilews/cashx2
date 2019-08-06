package cashx2.common.configurations;

public class AppConfiguration {
    private static AppConfiguration instance;
    private final int width = 1024;
    private final int height = 650;
    private final String appdata = System.getenv("APPDATA") +"\\CashX2";
    private final String seedFileDirectory = getAppdata().concat("\\seed.csi");
    private AppConfiguration() {
        //Nothing
    }
    public static synchronized AppConfiguration getInstance() {
        if(instance == null){
            instance = new AppConfiguration();
        }
        return instance;
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public String getAppdata() {
        return appdata;
    }
    public String getSeedFileDirectory(){ return seedFileDirectory; }
}
