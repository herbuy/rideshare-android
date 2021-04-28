package my_scripts.home_screen;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import core.businessobjects.Client;
import libraries.android.MakeDummy;


public class ClientDisplayScript2 {
    public static View run(Context context){
        final LinearLayout container = MakeDummy.linearLayoutVertical(context);
        loadIndicatorScript(context, container);
        clientDownloadScript(container);
        return container;

    }

    private static void clientDownloadScript(final LinearLayout container) {
        MakeDummy.handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        clientDisplayScript(makeFakeClientList(),container);
                    }
                }
                , 1000);
    }

    private static List<Client> makeFakeClientList() {
        List<Client> clientList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            clientList.add(new Client(
                    "Photo",
                    "Joe Kamwesi",
                    "USH 25000",
                    "+256 772 913 682"

            ));

        }
        return clientList;
    }

    private static void retryScript(String message, ViewGroup container) {
        MakeDummy.setContent(
                container,
                MakeDummy.textView(container.getContext(), message + "; Retry")
        );
    }

    private static void clientDisplayScript(List<Client> body, LinearLayout container) {
        if(body.size() < 1){
            listEmptyScript(container);
        }
        else{
            listNotEmptyScript(body, container);
        }

    }

    private static void listNotEmptyScript(List<Client> body, LinearLayout container) {

        MakeDummy.setContent(
                container,
                clientGridBuilderScript.run(container.getContext(), body)

                //MakeDummy.textView(container.getContext(),"Success")
        );
    }

    private static void listEmptyScript(ViewGroup container) {
        MakeDummy.setContent(
                container,
                MakeDummy.textView(container.getContext(),"No Clients")
        );
    }

    private static void loadIndicatorScript(Context context, LinearLayout container) {
        MakeDummy.setContent(container, MakeDummy.textView(context, "Loading"));
    }

}
