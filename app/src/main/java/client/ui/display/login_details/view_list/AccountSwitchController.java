package client.ui.display.login_details.view_list;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import client.data.AppCallback;
import client.data.Rest;
import client.ui.AccountSwitchedEvent;
import client.ui.display.Trip.tripList.FrameLayoutBasedHerbuyStateView;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForLogin;
import core.businessobjects.SessionDetails;
import core.businessobjects.UserLoginDetails;
import layers.render.Atom;
import libraries.android.MakeDummy;
import retrofit2.Call;
import retrofit2.Response;

public class AccountSwitchController implements HerbuyView {
    Context context;
    UserLoginDetails userLoginDetails;
    FrameLayoutBasedHerbuyStateView accountSwitchTrigger;

    public AccountSwitchController(Context context, UserLoginDetails userLoginDetails) {
        this.context = context;
        this.userLoginDetails = userLoginDetails;
        accountSwitchTrigger = new FrameLayoutBasedHerbuyStateView(context);
        MakeDummy.wrapContent(accountSwitchTrigger.getView());

        accountSwitchTrigger.render(buttonForSwitchToAccount());
    }

    private View buttonForSwitchToAccount() {
        return Atom.button(context, "Switch to this account", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountSwitchTrigger.render(busyMessage());

                ParamsForLogin paramsForLogin = new ParamsForLogin();
                paramsForLogin.setMobileNumber(userLoginDetails.getMobileNumber());
                paramsForLogin.setPassword(userLoginDetails.getPassword());

                Rest.api().login(paramsForLogin).enqueue(new AppCallback<List<SessionDetails>>() {
                    @Override
                    protected void onSuccess(Call<List<SessionDetails>> call, Response<List<SessionDetails>> response) {
                        accountSwitchTrigger.render(MakeDummy.textView(context, "Account now active"));
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                accountSwitchTrigger.render(buttonForSwitchToAccount());
                            }
                        }, 3000);

                        AccountSwitchedEvent.instance().notifyObservers(response.body().get(0));
                    }

                    @Override
                    protected void onError(Call<List<SessionDetails>> call, Throwable t) {
                        accountSwitchTrigger.render(MakeDummy.textView(context, "Failed switch to this account"));
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                accountSwitchTrigger.render(buttonForSwitchToAccount());
                            }
                        }, 3000);
                    }
                });


            }
        });
    }

    protected TextView busyMessage() {
        return MakeDummy.textView(context, "Switching account");
    }

    @Override
    public View getView() {
        return accountSwitchTrigger.getView();
    }
}
