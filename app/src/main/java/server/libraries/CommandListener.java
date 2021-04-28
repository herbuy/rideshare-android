package server.libraries;


        import java.util.HashMap;


public class CommandListener {

    public interface SerializeListener {

        String serialize(Object toSerialize);
    }
    private SerializeListener serializeListener;

    public void setSerializeListener(SerializeListener serializeListener) {
        this.serializeListener = serializeListener;
    }

    public interface SendOutputListener {

        void sendOutput(String toSend);
    }
    SendOutputListener sendOutputListener;

    public void setSendOutputListener(SendOutputListener sendOutputListener) {
        this.sendOutputListener = sendOutputListener;
    }

    public interface ListenerForGetOutput{
        Object getOutput() throws Exception ;
    }

    //============================================

    public interface SendErrorListener{
        void sendError(int statusCode, String statusText);
    }
    private SendErrorListener sendErrorListener;

    public void setSendErrorListener(SendErrorListener sendErrorListener) {
        this.sendErrorListener = sendErrorListener;
    }

    private void sendError(int statusCode, String statusText) {
        if(sendErrorListener != null){
            sendErrorListener.sendError(statusCode, statusText);
        }
    }
    //=========================

    public static boolean exists(String command){
        return false;
    }
    public static CommandListener get(String command){
        return new CommandListener();
    }



    public interface ListenerForGetCommandType{
        String getCommandType();
    }
    ListenerForGetCommandType listenerForGetCommandType;

    public void setListenerForGetCommandType(ListenerForGetCommandType listenerForGetCommandType) {
        this.listenerForGetCommandType = listenerForGetCommandType;
    }

    private String cmd = "";

    public String getCmd() {
        return cmd;
    }

    HashMap<String, ListenerForGetOutput> listenersForGetOutput = new HashMap<>();
    protected void addListenerForGetOutput(String command, ListenerForGetOutput listener){
        listenersForGetOutput.put(command, listener);
    }
    public void run() {
        try {

            //now we have some listeners, we can process the commands
            if(listenerForGetCommandType == null){
                return;
            }

            //sendError(400, "working 4: ");
            String cmdRaw = listenerForGetCommandType.getCommandType();


            if(cmdRaw == null){
                sendError(StatusCodes.ClientError.IllegalArgument, "Command not specified");
                return;
            }


            if("".equalsIgnoreCase(cmdRaw)){
                sendError(StatusCodes.ClientError.IllegalArgument, "Command not specified");
                return;
            }

            cmd = cmdRaw.trim();



            //we have a command, but not sure if supported
            if(listenersForGetOutput == null){
                return;
            }

            if(listenersForGetOutput.get(cmd) == null){
                sendError(StatusCodes.ClientError.NotExist, "Invalid command: "+cmd);
            }



            //command exists, lets getOrientation its output
            Object output = listenersForGetOutput.get(cmd).getOutput();
            if(output == null){
                sendError(400, "null output detected");
                return;
            }

            //got the output, lets serialize it
            if (serializeListener == null) {
                return;
            }

            //got output, try to serialize it
            String toSend = serializeListener.serialize(output); //later, we can put a try catch here to handle what happens if the output can not be serialized
            if (sendOutputListener == null) {
                return;
            }
            //output serialized, try to send the output


            sendOutputListener.sendOutput(toSend);


        } catch (Exception ex) {
            System.out.println("error getting output:\n"+ex.getMessage());
            sendError(StatusCodes.ClientError.IllegalArgument, ex.getMessage());
        }

    }



}

