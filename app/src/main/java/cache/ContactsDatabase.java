package cache;

import core.businessobjects.Contact;

public class ContactsDatabase extends AppDatabase<Contact> {

    @Override
    protected Class<Contact> getClassOfT() {
        return Contact.class;
    }

    @Override
    protected final String getTableName() {
        return "user_contacts_table";
    }
}
