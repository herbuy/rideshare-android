package client.ui.display.Location;

import android.content.Context;
import android.text.Editable;
import android.view.View;

import java.util.List;

import client.data.AppCallback;
import client.data.Rest;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForAddLocation;
import core.businessobjects.Location;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.TextChangedListener;
import libraries.android.MultiStateNetworkButton;
import retrofit2.Call;
import retrofit2.Response;

public abstract class FormForAddLocation implements HerbuyView {
    private Context context;
    private ParamsForAddLocation locationDetails = new ParamsForAddLocation();

    public FormForAddLocation(Context context) {
        this.context = context;
    }

    @Override
    public View getView() {
        return MakeDummy.linearLayoutVertical(
                context,
                name(),
                addLocation()
        );
    }

    private View addLocation() {
        return new MultiStateNetworkButton(context){
            String error = "";
            @Override
            protected View defaultView() {
                return Atom.button(context, "Add Location", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doCreateAccount();
                    }

                    private void doCreateAccount() {
                        changeViewToBusy();
                        Rest.api().addLocation(locationDetails).enqueue(new AppCallback<List<Location>>() {
                            @Override
                            protected void onSuccess(Call<List<Location>> call, Response<List<Location>> response) {
                                changeViewToSuccess();
                                onLocationAdded(response.body().get(0));
                            }

                            @Override
                            protected void onError(Call<List<Location>> call, Throwable t) {
                                error = t.getMessage();
                                changeViewToFailed();
                            }
                        });
                    }
                });
            }

            @Override
            protected View busyView() {
                return Atom.textView(context,"Registering Location...");
            }

            @Override
            protected View successView() {
                return Atom.textView(context,"Location added successfully");
            }

            @Override
            protected View errorView() {
                return Atom.textView(context,error);
            }
        }.getView();

    }

    protected abstract void onLocationAdded(Location location);

    private View name() {
        return Atom.editText(context, "Name", new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                locationDetails.setName(s.toString());
            }
        });
    }

}
