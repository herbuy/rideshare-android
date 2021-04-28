package client.ui.display.users;

import android.content.Context;
import android.view.View;

import java.util.List;

import client.ui.libraries.HerbuyGridView;
import core.businessobjects.User;
import resources.Dp;
import retrofit2.Call;

public abstract class UserGridView extends HerbuyGridView<User> {
    public UserGridView(Context context) {
        super(context);
        refresh();
    }

    public void refresh() {
        getApiCall().enqueue(this);
    }

    public abstract Call<List<User>> getApiCall();

    @Override
    protected Parameters getGridParameters() {
        return new Parameters(3, Dp.normal(),Dp.normal());
    }

    @Override
    protected View createItemView(User item) {

       return new UserView(getContext(),item).getView();
    }

}
