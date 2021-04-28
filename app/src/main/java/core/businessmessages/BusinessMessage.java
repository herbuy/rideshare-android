
package core.businessmessages;

public class BusinessMessage {


    public String getType() {
        return this.getClass().getSimpleName();
    }

}
