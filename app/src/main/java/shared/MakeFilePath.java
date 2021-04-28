package shared;

import config.DomainNameOrIPAndPort;

public class MakeFilePath {

    public static String fromFileShortName(String fileShortName) {
        return "http://"+ DomainNameOrIPAndPort.get() + "/RideServer/api/files/get/content/?fileShortNameWithExtension="+fileShortName;
    }
}
