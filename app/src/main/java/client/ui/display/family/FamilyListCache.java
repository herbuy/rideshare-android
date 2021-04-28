package client.ui.display.family;

import cache.LocalCache;
import core.businessobjects.Family;

/** represents a list of all families stored localy
 * shd be cleared whenever account switches
 *  */
public class FamilyListCache extends LocalCache<Family> {

    @Override
    protected Class<Family> getClassOfT() {
        return Family.class;
    }

    @Override
    protected final String getTableName() {
        return "familyListCache";
    }
}
