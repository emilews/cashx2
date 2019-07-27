package cashx2.common.configurations;

public class AppConfiguration {
    private static AppConfiguration instance;
    private AppConfiguration() {
        //Nothing
    }
    public static AppConfiguration getInstance() {
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

    private final int width = 1024;
    private final int height = 650;
}
