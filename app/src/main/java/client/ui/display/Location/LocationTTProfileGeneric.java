package client.ui.display.Location;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import client.data.AppCallback;
import client.data.Rest;
import client.ui.GotoActivity;
import client.ui.display.TabLayoutForApp;
import client.ui.libraries.HerbuyView;
import core.businessmessages.toServer.ParamsForGetLocations;
import core.businessobjects.Location;
import layers.render.Atom;
import libraries.android.MakeDummy;
import libraries.android.AdapterForFragmentPager;
import libraries.android.MultiStateNetworkGroupView;
import retrofit2.Call;
import retrofit2.Response;

public class LocationTTProfileGeneric implements HerbuyView {
    private AppCompatActivity context;
    private Location location;

    public LocationTTProfileGeneric(AppCompatActivity context, Location location) {
        this.context = context;
        this.location = location;
    }

    @Override
    public View getView() {
        return new TabLayoutForApp(context){
            @Override
            protected void onAddTabs(AdapterForFragmentPager adapter) {
                adapter.addTab(
                        "Basic Info",
                        basicInfo()
                );

                adapter.addTab(
                        "Update Info",
                        updateInfo()
                );

                adapter.addTab(
                        "Discover",
                        basicInfo()
                );

                adapter.addTab(
                        "Connect",
                        basicInfo()
                );

                adapter.addTab(
                        "Learn",
                        basicInfo()
                );

                adapter.addTab(
                        "Share",
                        basicInfo()
                );

                adapter.addTab(
                        "Accomodation",
                        basicInfo()
                );

                adapter.addTab(
                        "KYC",
                        basicInfo()
                );


            }
        }.getView();

    }

    private View basicInfo() {
        return new MultiStateNetworkGroupView(context){

            @Override
            protected ViewGroup createViewGroup() {
                return MakeDummy.linearLayoutVertical(context);
            }

            private String error = "";
            private List<View> successViews = new ArrayList<>();
            @Override
            protected void refresh() {
                changeViewToBusy();
                Rest.api().getLocations(paramsForGetLocations()).enqueue(new AppCallback<List<Location>>() {
                    @Override
                    protected void onSuccess(Call<List<Location>> call, Response<List<Location>> response) {
                        successViews.clear();
                        for(Location location: response.body()){
                            successViews.add(renderLocationInfo(location));
                        }
                        changeViewToSucessView();
                    }

                    private View renderLocationInfo(Location location) {
                        return Atom.textViewPrimaryBold(context,location.getName());
                    }

                    @Override
                    protected void onError(Call<List<Location>> call, Throwable t) {
                        error = t.getMessage();
                        changeViewToErrorView();
                    }
                });
            }

            private ParamsForGetLocations paramsForGetLocations() {
                ParamsForGetLocations paramsForGetLocations = new ParamsForGetLocations();
                paramsForGetLocations.setLocationIdEquals(location.getId());
                return paramsForGetLocations;
            }

            @Override
            protected View getBusyView() {
                return Atom.textView(context,"Loading...");
            }

            @Override
            protected View getErrorView() {
                return Atom.textView(context,error);
            }

            @Override
            protected List<View> getSuccessView() {
                return successViews;
            }

        }.getView();


    }
    private View updateInfo() {

        //return MakeDummy.textView(context,"Location to update"+location.getName());


        return new FormForUpdateLocation(context,location){
            @Override
            protected void onLocationUpdated(Location location) {

                GotoActivity.locationProfileGeneric(context, location);
            }
        }.getView();
    }
}
