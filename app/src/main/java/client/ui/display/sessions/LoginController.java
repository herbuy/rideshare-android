package client.ui.display.sessions;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import java.util.List;
import client.data.AppCallback;
import client.data.Rest2;
import client.ui.GotoActivity;
import client.ui.AccountSwitchedEvent;
import core.businessmessages.toServer.ParamsForLogin;
import core.businessobjects.SessionDetails;
import retrofit2.Call;
import retrofit2.Response;


public class LoginController {

    LoginForm loginForm;

    public LoginController(AppCompatActivity context) {

        loginForm = new LoginForm(context) {
            @Override
            protected void onLoginClick(ParamsForLogin paramsForLogin) {
                loginForm.renderBusy();
                Rest2.api().login(paramsForLogin).enqueue(new AppCallback<List<SessionDetails>>() {
                    @Override
                    protected void onSuccess(Call<List<SessionDetails>> call, Response<List<SessionDetails>> response) {
                        loginForm.renderSuccess();
                        AccountSwitchedEvent.instance().notifyObservers(response.body().get(0));

                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                context.finish();
                                GotoActivity.tripList(context);
                            }
                        }, 1000);
                    }

                    @Override
                    protected void onError(Call<List<SessionDetails>> call, Throwable t) {
                        loginForm.renderError(t.getMessage());
                    }
                });
            }
        };
    }

    public View getView() {
        return loginForm.getView();
    }
}
