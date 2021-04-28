package com.skyvolt.jabber;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import java.util.ArrayList;
import libraries.android.ReadContacts;
import shared.BaseActivity;

public class ReadContactsActivity extends BaseActivity {

    ListView list;
    ArrayList<ReadContacts.Contact> contactList;
    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_contacts);

        //if we have the permission to read the contacts
        if (ReadContacts.granted(this)) {
            contactList = ReadContacts.run(this);
            Log.d("LIST SIZE: ", contactList.size()+"");
            for(ReadContacts.Contact contact: contactList){
                if(contact.hasPhoneNumber()){
                    Log.d("ITEM: ", contact.getDisplayName() + ": " + contact.getPhoneNumberList().get(0));
                }

            }

        } else {
            //otherwise
            ReadContacts.requestPermission(this);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        contactList = ReadContacts.run(requestCode,permissions,grantResults,this);
    }

}
