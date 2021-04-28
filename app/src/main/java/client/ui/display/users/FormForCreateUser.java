package client.ui.display.users;

import android.content.Context;
import android.text.Editable;
import android.view.View;

import java.util.List;

import client.data.AppCallback;
import client.data.Rest2;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForCreateUser;
import core.businessobjects.User;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.TextChangedListener;
import libraries.android.MultiStateNetworkButton;
import retrofit2.Call;
import retrofit2.Response;

public abstract class FormForCreateUser implements HerbuyView {
    private Context context;
    private ParamsForCreateUser userDetails = new ParamsForCreateUser();

    public FormForCreateUser(Context context) {
        this.context = context;
    }

    @Override
    public View getView() {
        return MakeDummy.linearLayoutVertical(
                context,
                username(),
                mobile(),
                password(),
                createAccount()
        );
    }

    private View createAccount() {
        return new MultiStateNetworkButton(context){
            String error = "";
            @Override
            protected View defaultView() {
                return Atom.button(context, "Create Account", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doCreateAccount();
                    }

                    private void doCreateAccount() {
                        changeViewToBusy();
                        Rest2.api().createUser(userDetails).enqueue(new AppCallback<List<User>>() {
                            @Override
                            protected void onSuccess(Call<List<User>> call, Response<List<User>> response) {
                                changeViewToSuccess();
                                onAccountCreated(response.body().get(0));
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
                return Atom.textView(context,"Creating account...");
            }

            @Override
            protected View successView() {
                return Atom.textView(context,"GREAT!! Account Created");
            }

            @Override
            protected View errorView() {
                return Atom.textView(context,error);
            }
        }.getView();

    }

    protected abstract void onAccountCreated(User user);

    private View username() {
        return Atom.editText(context, "Name", new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                userDetails.setUserName(s.toString());
            }
        });
    }
    private View mobile() {
        return Atom.editText(context, "Mobile Number", new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                userDetails.setMobileNumber(s.toString());
            }
        });
    }
    private View password() {
        return Atom.password(context, "Password", new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                userDetails.setPassword(s.toString());
            }
        });
    }
}
