
package core.businessmessages.fromServer;

public class ServerResponse {
    private String error = "";
    private Object response;

    public ServerResponse(String error, Object response) {
        this.error = error;
        this.response = response;
    }

    public String getError() {
        return error;
    }

    public Object getResponse() {
        return response;
    }


}
