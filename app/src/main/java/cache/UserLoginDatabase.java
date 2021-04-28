package cache;

import core.businessobjects.UserLoginDetails;

public class UserLoginDatabase extends AppDatabase<UserLoginDetails> {

    @Override
    protected Class<UserLoginDetails> getClassOfT() {
        return UserLoginDetails.class;
    }

    @Override
    protected final String getTableName() {
        return "user_login_table";
    }
}
