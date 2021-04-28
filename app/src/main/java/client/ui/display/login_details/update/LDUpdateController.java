package client.ui.display.login_details.update;

import android.content.Context;
import android.view.View;

import java.util.List;

import client.data.AppCallback;
import client.data.Rest;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForUpdateLoginDetails;
import core.businessobjects.UserLoginDetails;
import retrofit2.Call;
import retrofit2.Response;

public class LDUpdateController implements HerbuyView {

    private LDUpdateForm ldUpdateForm;

    public LDUpdateController(Context context, UserLoginDetails userLoginDetails) {
        ldUpdateForm = new LDUpdateForm(context,userLoginDetails){
            @Override
            protected void onUpdateLoginDetails(ParamsForUpdateLoginDetails paramsForUpdateLoginDetails) {
                ldUpdateForm.renderBusy();
                Rest.api().updateLoginDetails(paramsForUpdateLoginDetails).enqueue(new AppCallback<List<UserLoginDetails>>() {
                    @Override
                    protected void onSuccess(Call<List<UserLoginDetails>> call, Response<List<UserLoginDetails>> response) {
                        ldUpdateForm.render(null);
                    }

                    @Override
                    protected void onError(Call<List<UserLoginDetails>> call, Throwable t) {
                        ldUpdateForm.renderError(t.getMessage(),null);
                    }
                });
            }
        };

    }



    @Override
    public View getView() {

        return ldUpdateForm.getView();
    }
}
