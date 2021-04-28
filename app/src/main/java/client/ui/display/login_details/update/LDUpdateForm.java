package client.ui.display.login_details.update;

import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import core.businessmessages.toServer.ParamsForUpdateLoginDetails;
import core.businessobjects.UserLoginDetails;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.TextChangedListener;
import libraries.android.HerbuyStateView;

public abstract class LDUpdateForm implements HerbuyStateView{
    private Context context;
    UserLoginDetails userLoginDetails;
    ParamsForUpdateLoginDetails paramsForUpdateLoginDetails = new ParamsForUpdateLoginDetails();
    TextView txtStatus;

    public LDUpdateForm(Context context, UserLoginDetails userLoginDetails) {
        this.context = context;
        this.userLoginDetails = userLoginDetails;
        paramsForUpdateLoginDetails.setUserId(userLoginDetails.getUserId());
        txtStatus = new TextView(context);
    }

    @Override
    public View getView() {
        return MakeDummy.linearLayoutVertical(
                context,
                mobileNumber(),
                password(),
                Atom.button(context, "Update", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onUpdateLoginDetails(paramsForUpdateLoginDetails);
                    }
                }),
                txtStatus
        );
    }

    protected abstract void onUpdateLoginDetails(ParamsForUpdateLoginDetails paramsForUpdateLoginDetails);

    private View password() {
        EditText password = Atom.password(context, "Password",userLoginDetails.getPassword(), new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                paramsForUpdateLoginDetails.setMobileNumber(s.toString());
            }
        });

        return password;
    }

    private View mobileNumber() {
        EditText mobileNumber = Atom.editText(context, "Mobile Number",userLoginDetails.getMobileNumber(), new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                paramsForUpdateLoginDetails.setMobileNumber(s.toString());
            }
        });

        return mobileNumber;
    }

    @Override
    public void render(View content) {
        txtStatus.setText("Login details updated!!");
    }

    @Override
    public void renderError(String message, OnRetryLoad onRetryLoad) {
        txtStatus.setText(message);
    }

    @Override
    public void renderBusy() {
        txtStatus.setText("Updating...");
    }
}
