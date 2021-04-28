
package core.businessmessages;

import java.util.ArrayList;

public class ArrayListFromServer<T>  extends BusinessMessageFromServer{
    private ArrayList<T> result;

    public ArrayListFromServer(ArrayList<T> result) {
        this.result = result;
    }

    public ArrayList<T> getResult() {
        return result;
    }
}
