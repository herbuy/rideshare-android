package libraries.android;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

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
public class ObjectTaskPermit0 {

    private static final int REQUEST_PERMISSION = 0;
    Activity context;
    Callback callback;

    public ObjectTaskPermit0(Activity context, Callback callback) {
        this.context = context;
        this.callback = callback;
    }

    List<String> requiredPermissions = new ArrayList<>();

    /**
     * adds a permission to the list of permissions your activity requires.
     * Call it before checking if the permissions you requie are granted
     */
    public void addPermision(String permission) {
        requiredPermissions.add(permission);
    }

    public void addReadExternalStorage() {
        requiredPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public void addWriteExternalStorage() {
        requiredPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * checks whether all required permissions are granted. returns false if any is denied
     */
    public boolean granted() {
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
    public void request() {
        List<String> ungrantedPermissions = getUngrantedPermissions();
        String[] permissionsToRequest = new String[ungrantedPermissions.size()];
        permissionsToRequest = ungrantedPermissions.toArray(permissionsToRequest);
        ActivityCompat.requestPermissions(context, permissionsToRequest, REQUEST_PERMISSION);
    }

    public void requestThenRun(Runnable runnable) {
        if(granted()){
            runnable.run();
        }
        else{
            request();
        }
    }

    public interface Callback {
        /** will be called if the user grants all the permissions you request */
        void allRequestedPermissionsGranted();
        /** will be called if the user rejects some of the permissions you request */
        void someRequestedPermissionsDenied();
    }

    public void processPermissionsResult(int requestCode, String[] requestedPermissions, int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION) {
            boolean allRequestedPermissionsGranted = true;
            List<String> grantedPermissions = new ArrayList<>();
            for (int i = 0; i < requestedPermissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                   allRequestedPermissionsGranted = false;
                    break;
                }
            }
            if(allRequestedPermissionsGranted){
                notifyAllPermissionsGranted();
            }
            else{
                notifySomePermissionsDenied();
            }

        }
    }

    private void notifyAllPermissionsGranted(){
        if(callback != null){
            callback.allRequestedPermissionsGranted();
        }
    }

    private void notifySomePermissionsDenied(){
        if(callback != null){
            callback.someRequestedPermissionsDenied();
        }
    }


}
