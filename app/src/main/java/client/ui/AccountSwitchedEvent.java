package client.ui;

import core.businessobjects.SessionDetails;
import libraries.ObserverList;

public class AccountSwitchedEvent {
    private static ObserverList<SessionDetails> observerList = new ObserverList<>();

    public static ObserverList<SessionDetails> instance(){
        return observerList;
    }
}
