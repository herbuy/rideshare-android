
package core.businessmessages;

public class BusinessMessageToServer extends BusinessMessage{
    protected String sessionId = "";
    protected String className = getClass().getSimpleName();

    public String getClassName(){
        return className;
    }


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
