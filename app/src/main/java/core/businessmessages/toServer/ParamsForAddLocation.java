package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForAddLocation extends BusinessMessageToServer{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
