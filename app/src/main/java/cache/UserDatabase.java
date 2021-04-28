package cache;

import core.businessobjects.User;

public class UserDatabase extends AppDatabase<User> {

    @Override
    protected Class<User> getClassOfT() {
        return User.class;
    }

    @Override
    protected final String getTableName() {
        return "user_database";
    }
}
