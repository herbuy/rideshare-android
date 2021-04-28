
package core.validators;

import core.businessmessages.toServer.ParamsForLogin;

/** maps different error conditions to different handlers
 * separation useful because...
 * at client, errors related to password could be shown near password field
 * while errors related to mobile number could be displayed near mobile number
 *
 */

public class ValidatorForLoginParams {

    public final void run(ParamsForLogin params) throws Exception {
        if(params == null){
            parametersNotProvided();
            return;
        }

        if(params.getMobileNumber() == null || params.getMobileNumber().trim().equalsIgnoreCase("")){
            mobileNumberNotProvided();
            return;
        }

        if(params.getPassword() == null || params.getPassword().trim().equalsIgnoreCase("")){
            passwordNotProvided();
            return;
        }

        onSuccess(params);

    }

    protected void onSuccess(ParamsForLogin params) {

    }

    /** Can override what you what to do in case of various error events or validation events */
    protected void parametersNotProvided(){
        throw new RuntimeException("Login parameters not provided");
    }

    protected void mobileNumberNotProvided(){
        throw new RuntimeException("Mobile number not provided");
    }

    protected void passwordNotProvided(){
        throw new RuntimeException("Password not provided");
    }

}
