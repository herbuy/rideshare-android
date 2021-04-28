package core.businessmessages.toServer;

import core.businessmessages.BusinessMessageToServer;

public class ParamsForGetUsers extends BusinessMessageToServer{
    private String addableByUserIdEquals = "";



    //private String isFriendEquals; //if select left empty, not applied, if 1, only friend, if 0, not friend
    //private String beenToDestinationIdEquals; //if select left empty, not applied, if id specified, only those users who have been to the specified destination

    //private String travelledTogetherEquals;


    public String getAddableByUserIdEquals() {
        return addableByUserIdEquals;
    }

    public void setAddableByUserIdEquals(String addableByUserIdEquals) {
        this.addableByUserIdEquals = addableByUserIdEquals;
    }
}
