package my_scripts.home_screen;

import android.content.Context;
import android.view.View;

import libraries.android.MessageBox;

public class SearchButtonDisplayScript {
    public static View run(final Context context){
        return ButtonDisplayScript.run(context, "Search For Client", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageBox.show("Will search for a client",context);
            }
        });
    }
}
