package client.ui.display.family;

import java.util.ArrayList;
import java.util.List;

import core.businessobjects.FamilyMessage;


 //where we store family messages offline
public class MessageCacheForFamily {
    private String familyId = "";
    private List<FamilyMessage> list = new ArrayList<>();

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public List<FamilyMessage> getList() {
        return list;
    }

}
