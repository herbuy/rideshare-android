package client.ui.display.users;

import android.content.Context;
import android.text.Editable;
import android.view.View;

import java.util.List;

import client.data.AppCallback;
import client.data.Rest;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForUpdateUser;
import core.businessobjects.User;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.TextChangedListener;
import libraries.android.MultiStateNetworkButton;
import retrofit2.Call;
import retrofit2.Response;

public abstract class FormForUpdateUser implements HerbuyView {
    private Context context;
    private ParamsForUpdateUser userDetails = new ParamsForUpdateUser();
    private User user;

    public FormForUpdateUser(Context context, User user) {
        this.context = context;
        this.user = user;
    }


    @Override
    public View getView() {
        userDetails.setUserId(user.getUserId());
        userDetails.setUserName(user.getUserName());
        userDetails.setMobileNumber(user.getMobileNumber());

        return MakeDummy.linearLayoutVertical(
                context,
                username(),
                mobile(),
                updateInfo()
        );
    }

    private View updateInfo() {
        return new MultiStateNetworkButton(context){
            String error = "";
            @Override
            protected View defaultView() {
                return Atom.button(context, "Update Info", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doUpdateInfo();
                    }

                    private void doUpdateInfo() {
                        changeViewToBusy();
                        Rest.api().updateUser(userDetails).enqueue(new AppCallback<List<User>>() {
                            @Override
                            protected void onSuccess(Call<List<User>> call, Response<List<User>> response) {
                                changeViewToSuccess();
                                onAccountUpdated(response.body().get(0));
                            }

                            @Override
                            protected void onError(Call<List<User>> call, Throwable t) {
                                error = t.getMessage();
                                changeViewToFailed();
                            }
                        });
                    }
                });
            }

            @Override
            protected View busyView() {
                return Atom.textView(context,"Updating account...");
            }

            @Override
            protected View successView() {
                return Atom.textView(context,"Account info updated");
            }

            @Override
            protected View errorView() {
                return Atom.textView(context,error);
            }
        }.getView();

    }

    protected abstract void onAccountUpdated(User user);

    private View username() {
        return Atom.editText(context, "Name",userDetails.getUserName(), new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                userDetails.setUserName(s.toString());
            }
        });
    }
    private View mobile() {
        return Atom.editText(context, "Mobile Number", userDetails.getMobileNumber(), new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                userDetails.setMobileNumber(s.toString());
            }
        });
    }

}
