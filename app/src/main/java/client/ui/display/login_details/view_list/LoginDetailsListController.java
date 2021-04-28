package client.ui.display.login_details.view_list;

import android.content.Context;
import android.view.View;
import java.util.List;
import client.data.AppCallback;
import client.data.Rest;
import client.ui.GotoActivity;
import core.businessobjects.UserLoginDetails;
import libraries.android.HerbuyStateView;
import retrofit2.Call;
import retrofit2.Response;

public class LoginDetailsListController {

    private HostForLoginDetailsList loginDetailsListHost;

    private Context context;

    public LoginDetailsListController(Context context) {
        this.context = context;
        loginDetailsListHost = new HostForLoginDetailsList(context);
    }

    public View getView() {
        init();
        return loginDetailsListHost.getView();
    }

    public void init() {
        loginDetailsListHost.renderBusy();

        Rest.api().getUserLoginDetails().enqueue(new AppCallback<List<UserLoginDetails>>() {
            @Override
            protected void onSuccess(Call<List<UserLoginDetails>> call, Response<List<UserLoginDetails>> response) {

                LoginDetailsListView lst = new LoginDetailsListView(context){
                    @Override
                    protected void onUpdateLoginDetails(UserLoginDetails item) {
                        GotoActivity.updateLoginDetails(context, item);
                    }

                };
                lst.setContent(response.body());


                loginDetailsListHost.render(
                        lst.getView()

                );
            }

            @Override
            protected void onError(Call<List<UserLoginDetails>> call, Throwable t) {
                loginDetailsListHost.renderError(t.getMessage(), new HerbuyStateView.OnRetryLoad() {
                    @Override
                    public void run() {
                        init();
                    }
                });

            }
        });
    }
}
