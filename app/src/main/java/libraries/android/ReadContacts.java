package libraries.android;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import androidx.core.app.ActivityCompat;
import android.telephony.PhoneNumberUtils;

import java.util.ArrayList;
import java.util.List;


public class ReadContacts {

    public static final int REQUEST_READ_CONTACTS = 79;

    public static boolean granted(Context context) {
        return ActivityCompat.checkSelfPermission(
                context, android.Manifest.permission.READ_CONTACTS
        )
                == PackageManager.PERMISSION_GRANTED;
    }

    public static ArrayList<Contact> run(Context context) {
        ArrayList<Contact> contactList = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur != null && cur.getCount() > 0) {
            while (cur.moveToNext()) {
                Contact contact = new Contact();
                contact.setId(
                        cur.getString(
                                cur.getColumnIndex(ContactsContract.Contacts._ID)
                        )
                );

                contact.setDisplayName(
                        cur.getString(
                                cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        )
                );

                if (hasPhoneNumber(cur)) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{contact.getId()}, null);
                    while (pCur.moveToNext()) {

                        String rawPhoneNumber = pCur.getString(
                                pCur.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER
                                )
                        );

                        if(PhoneNumberUtils.isGlobalPhoneNumber(rawPhoneNumber)){
                            contact.getPhoneNumberList().add(
                                    rawPhoneNumber
                            );
                        }




                    }
                    pCur.close();
                }

                contactList.add(contact);
            }
        }
        if (cur != null) {
            cur.close();
        }
        return contactList;
    }

    private static boolean hasPhoneNumber(Cursor cur) {
        return cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0;
    }


    public static ArrayList<Contact> run(int requestCode,
                                        String permissions[], int[] grantResults, Context context) {
        switch (requestCode) {
            case ReadContacts.REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    return ReadContacts.run(context);

                } else {
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }

            }
        }
        return new ArrayList<>();
    }

    public static void requestPermission(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.READ_CONTACTS)) {
            // show UI part if you want here to show some rationale !!!
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.READ_CONTACTS)) {
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
    }

    public static class Contact {
        private String id;
        private String displayName;
        private List<String> phoneNumberList = new ArrayList<>();

        public Contact() {
        }

        public String getId() {
            return id;
        }

        public String getDisplayName() {
            return displayName;
        }

        public List<String> getPhoneNumberList() {
            return phoneNumberList;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }


        public boolean hasPhoneNumber() {
            return getPhoneNumberList().size() > 0;
        }
    }

}

