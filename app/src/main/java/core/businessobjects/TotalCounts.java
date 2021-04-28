package core.businessobjects;

/**
 * returns the number of entities a customer or business has, such as contacts
 * could be used to find how many enttities
 * recommended that u check that a customer has some contacts before you try to
 * perform certain operations like scheduling their trip else nobody will receive notification
 *
 * */
public class TotalCounts {
    private String userId;
    private int contacts;

    public int getContacts() {
        return contacts;
    }

    public void setContacts(int contacts) {
        this.contacts = contacts;
    }
}
