package cashx2.app;

import com.sun.javafx.application.LauncherImpl;

public class Main {
    public static void main(String[] args) {
        LauncherImpl.launchApplication(AppMain.class, WholePreloader.class, args);
    }
}
