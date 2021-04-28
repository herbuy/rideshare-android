package client.ui.display.users;

import android.os.Bundle;
import android.view.View;

import client.ui.GotoActivity;
import layers.render.Atom;
import libraries.android.MakeDummy;
import shared.BaseActivity;

public class AboutMeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(contentView());
    }

    private View contentView() {
        return MakeDummy.linearLayoutVertical(
                this,
                aboutCurrentUser(),
                changeProfilePic()
        );
    }

    private View changeProfilePic() {
        return Atom.button(this, "Change Profile Picture", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoActivity.changeProfilePic(AboutMeActivity.this);
            }
        });
    }


    private View aboutCurrentUser() {
        return Atom.textView(this,"About Me");
    }

}
