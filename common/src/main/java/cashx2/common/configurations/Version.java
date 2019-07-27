package cashx2.common.configurations;

public class Version {
    private static Version instance;
    private Version() {
        //Nothing
    }
    public static Version getInstance() {
        if(instance == null){
            instance = new Version();
        }
        return instance;
    }

    public String getAppName() {
        return appName;
    }

    public double getAppVersion() {
        return appVersion;
    }

    public double getServerVersion() {
        return serverVersion;
    }

    private final String appName = "CashX";
    //V1 is bisq based, V2 is NOT backwards compatible, and CAN'T recover anything from V1
    private final double appVersion = 2.0;
    //Server not yet implemented, but it should be the same as V1
    private final double serverVersion = 1.0;
}
