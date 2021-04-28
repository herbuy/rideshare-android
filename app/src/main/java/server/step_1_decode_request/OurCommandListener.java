package server.step_1_decode_request;

import server.libraries.CommandListener;

public class OurCommandListener extends CommandListener {
    public OurCommandListener() {
        setSerializeListener(new SerializeListener() {
            @Override
            public String serialize(Object toSerialize) {
                return null;
            }
        });

        setSendOutputListener(new SendOutputListener() {
            @Override
            public void sendOutput(String toSend) {

            }
        });

        setSendErrorListener(new SendErrorListener() {
            @Override
            public void sendError(int statusCode, String statusText) {

            }
        });
    }

}
