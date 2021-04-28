package client.ui.display.users;

import core.businessobjects.FileDetails;
import libraries.ObserverList;

public class ProfilePhotoChangedEvent {
    private static ObserverList<FileDetails> observerList = new ObserverList<>();

    public static ObserverList<FileDetails> instance(){
        return observerList;
    }
}
