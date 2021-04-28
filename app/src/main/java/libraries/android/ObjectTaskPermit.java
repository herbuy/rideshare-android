package libraries.android;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** a simplified wrapper around the permission API, represents a permit to perform certain tasks,
 * which spells out the permission it requires, those granted, and can check if all permissions
 * granted before performing a task, or else request the missing permissions and take the action you
 * want to be performed in case all requested permissions are granted or if some permissions are denied.
 *
 * We opted to callit 'ObjectTaskPermit' rather than other simpler names like 'Task Permit'
 * to re-inforce the idea that it is best used to represent a a combination of both the
 * object of the permissions and the task.
 * As such, names of created object are best if in the form [Object][Tasks]Permit
 * e.g fileReadWritePermit, internetAccessPermit, etc.
 *
 *  */
public class ObjectTaskPermit {

    private static final int REQUEST_PERMISSION = 0;
    Activity context;
    Callback callback;

    public ObjectTaskPermit(Activity context) {
        this.context = context;
    }

    /** it is ok to add duplicates [due to overlaps], for the set data structure automatically weeds them out */
    Set<String> requiredPermissions = new HashSet<>();

    /**
     * adds a permission to the list of permissions your activity requires.
     * Call it before checking if the permissions you requie are granted
     *
     * ** it is ok to add duplicates [due to overlaps], for the set data structure automatically weeds them out
     */
    public ObjectTaskPermit addPermision(String permission) {
        requiredPermissions.add(permission);
        return this;
    }

    public ObjectTaskPermit addReadExternalStorage() {
        return addPermision(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public ObjectTaskPermit addWriteExternalStorage() {
        return addPermision(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /** short cut for adding the group of permissions required to read or write to external storage  */
    public ObjectTaskPermit addGroupReadWriteExternalStorage() {
        addReadExternalStorage();
        addWriteExternalStorage();
        return this;
    }

    public ObjectTaskPermit addInternet() {
        return addPermision(Manifest.permission.INTERNET);
    }
    public ObjectTaskPermit addAccessFineLocation() {
        return addPermision(Manifest.permission.ACCESS_FINE_LOCATION);
    }
    public ObjectTaskPermit addAccessCoarseLocation() {
        return addPermision(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public ObjectTaskPermit addGroupLocationAccess() {
        addInternet();
        addAccessFineLocation();
        addAccessCoarseLocation();
        return this;
    }

    /**
     * checks whether all required permissions are granted. returns false if any is denied
     */
    private boolean granted() {
        return getUngrantedPermissions().isEmpty();
    }

    private List<String> getUngrantedPermissions() {
        List<String> ungrantedPermissions = new ArrayList<>();
        for (String permission : requiredPermissions) {
            if (ActivityCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                ungrantedPermissions.add(permission);
            }
        }
        return ungrantedPermissions;
    }

    /**
     * called when you want to request for the added permissions. Best called after checking if permissions granted
     */
    private void request() {
        List<String> ungrantedPermissions = getUngrantedPermissions();
        String[] permissionsToRequest = new String[ungrantedPermissions.size()];
        permissionsToRequest = ungrantedPermissions.toArray(permissionsToRequest);
        ActivityCompat.requestPermissions(context, permissionsToRequest, REQUEST_PERMISSION);
    }


    public void requestThenRun(Callback callback) {
        if(callback == null){
            return;
        }

        this.callback = callback;
        if(granted()){
            callback.ifAllGranted();
        }
        else{
            request();
        }
    }



    public interface Callback {
        /** will be called if the user grants all the permissions you request */
        void ifAllGranted();
        /** will be called if the user rejects some of the permissions you request */
        void ifSomeDenied();
    }

    public void processPermissionsResult(int requestCode, String[] requestedPermissions, int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION) {
            if(granted()){
                notifyAllPermissionsGranted();
            }
            else{
                notifySomePermissionsDenied();
            }

        }
    }

    private void notifyAllPermissionsGranted(){
        if(callback != null){
            callback.ifAllGranted();
        }
    }

    private void notifySomePermissionsDenied(){
        if(callback != null){
            callback.ifSomeDenied();
        }
    }


}
