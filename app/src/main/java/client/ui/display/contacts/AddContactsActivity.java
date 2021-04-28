package client.ui.display.contacts;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import shared.BaseActivity;

public class AddContactsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Contacts");
        setContentView(new ContactsToAdd(this).getView());
    }
}
