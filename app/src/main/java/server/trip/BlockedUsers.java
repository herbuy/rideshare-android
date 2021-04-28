package server.trip;

import java.util.List;

import cache.BlockedUsersDatabase;
import core.businessobjects.BlockedUser;
import libraries.data.LocalDatabase;

/* returns blocked users matching criteria */
public class BlockedUsers {
    private boolean notFound = false;
    private List<BlockedUser> users;

    private BlockedUsers(){
    }

    //use this method to instantiate
    public static BlockedUsers where(final String blockedUserId, final String blockingUserId){
        BlockedUsers instance = new BlockedUsers();
        instance.init(blockedUserId,blockingUserId);
        return instance;
    }

    private void init(final String blockedUserId, final String blockingUserId) {
        List<BlockedUser> results = new BlockedUsersDatabase().select(new LocalDatabase.Where<BlockedUser>() {
            @Override
            public boolean isTrue(BlockedUser blockedUser) {
                return blockedUser.isUserId(blockedUserId) && blockedUser.isBlockedByUserId(blockingUserId);
            }
        });

        if(results == null || results.size() < 1){
            notFound = true;
        }
        else{
            users = results;
        }
    }

    public boolean isNotFound() {
        return notFound;
    }

    public boolean isFound() {
        return !isNotFound();
    }

    public List<BlockedUser> getUsers() {
        return users;
    }
}
