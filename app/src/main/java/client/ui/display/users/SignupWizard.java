package client.ui.display.users;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import java.util.List;

import client.data.AppCallback;
import client.data.Rest2;
import client.ui.GotoActivity;
import core.businessmessages.toServer.ParamsForRequestAccount;
import core.businessmessages.toServer.ParamsForVerifyAccount;
import core.businessobjects.User;
import retrofit2.Call;
import retrofit2.Response;

public class SignupWizard {

    SignupForm2 signupForm;

    public SignupWizard(AppCompatActivity context) {

        signupForm = new SignupForm2(context) {
            @Override
            protected void onRequestAccount(final SignupForm2 sender, ParamsForRequestAccount paramsForRequestAccount) {
                sender.indicateRequestingAccount();
                Rest2.api().requestAccount(paramsForRequestAccount).enqueue(callbackForRequestAccount(sender));
            }

            private AppCallback<List<String>> callbackForRequestAccount(final SignupForm2 sender) {
                return new AppCallback<List<String>>() {
                    @Override
                    protected void onSuccess(Call<List<String>> call, Response<List<String>> response) {
                        sender.onAccountRequestSuccessful();
                    }

                    @Override
                    protected void onError(Call<List<String>> call, Throwable t) {
                        sender.onAccountRequestFailed(t.getMessage());

                    }
                };
            }


            @Override
            protected void onClickCompleteSignup(final SignupForm2 sender, ParamsForVerifyAccount paramsForVerifyAccount) {
                sender.indicateCompletingSignup();
                Rest2.api().verifyAccount(paramsForVerifyAccount).enqueue(
                        new AppCallback<List<User>>() {
                            @Override
                            protected void onSuccess(Call<List<User>> call, Response<List<User>> response) {
                                sender.indicateAccountCreatedSuccessfully();

                                new android.os.Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        context.finish();
                                        GotoActivity.login(context);
                                    }
                                }, 1000);
                            }

                            @Override
                            protected void onError(Call<List<User>> call, Throwable t) {
                                sender.indicateErrorCreatingAccount(t.getMessage());

                            }
                        }
                );

            }
        };


    }

    public View getView() {
        return signupForm.getView();
    }

    public void updateOTP(String otp) {
        signupForm.updateVerificationCode(otp);
    }
}
