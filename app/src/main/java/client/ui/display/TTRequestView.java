package client.ui.display;

import android.content.Context;
import android.view.View;

import java.util.List;

import client.data.AppCallback;
import client.ui.IntentExtras;
import client.ui.libraries.HerbuyView;
import core.businessobjects.Proposal;
import retrofit2.Call;
import retrofit2.Response;

public abstract class TTRequestView implements HerbuyView {
    private Context context;
    private Proposal item;


    public TTRequestView(Context context, Proposal item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public View getView() {
        return null;
    }

    protected abstract void onSelectCancelTTRequest(TTRequestView sender, Proposal proposal);
    protected abstract void onSelectAcceptTTRequest(TTRequestView sender,Proposal proposal);
    protected abstract void onSelectRejectTTRequest(TTRequestView sender,Proposal proposal);
    protected abstract void onSelectChat(IntentExtras.ChatParameters chatParameters);

    private final AppCallback<List<Proposal>> callbackForCancelTTRequest = new AppCallback<List<Proposal>>() {
        @Override
        protected void onSuccess(Call<List<Proposal>> call, Response<List<Proposal>> response) {

        }

        @Override
        protected void onError(Call<List<Proposal>> call, Throwable t) {

        }
    };

    private final AppCallback<List<Proposal>> callbackForAcceptTTRequest = new AppCallback<List<Proposal>>() {
        @Override
        protected void onSuccess(Call<List<Proposal>> call, Response<List<Proposal>> response) {

        }

        @Override
        protected void onError(Call<List<Proposal>> call, Throwable t) {

        }
    };

    private final AppCallback<List<Proposal>> callbackForRejectTTRequest = new AppCallback<List<Proposal>>() {
        @Override
        protected void onSuccess(Call<List<Proposal>> call, Response<List<Proposal>> response) {

        }

        @Override
        protected void onError(Call<List<Proposal>> call, Throwable t) {

        }
    };

    public AppCallback<List<Proposal>> getCallbackForCancelTTRequest() {
        return callbackForCancelTTRequest;
    }

    public AppCallback<List<Proposal>> getCallbackForAcceptTTRequest() {
        return callbackForAcceptTTRequest;
    }

    public AppCallback<List<Proposal>> getCallbackForRejectTTRequest() {
        return callbackForRejectTTRequest;
    }
}
