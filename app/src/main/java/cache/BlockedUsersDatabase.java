package cache;

import core.businessobjects.BlockedUser;

public class BlockedUsersDatabase extends AppDatabase<BlockedUser> {

    @Override
    protected Class<BlockedUser> getClassOfT() {
        return BlockedUser.class;
    }

    @Override
    protected final String getTableName() {
        return "blocked_users_table";
    }
}
