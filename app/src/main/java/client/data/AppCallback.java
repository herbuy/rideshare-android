package client.data;

import android.os.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class AppCallback<T> implements Callback<T> {
    private static Handler handler = new Handler();

    @Override
    public final void onResponse(final Call<T> call, final Response<T> response) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onSuccess(call, response);
            }
        });
    }

    @Override
    public final void onFailure(final Call<T> call, final Throwable t) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                onError(call, t);
            }
        });
    }


    protected abstract void onSuccess(Call<T> call, Response<T> response);
    protected abstract void onError(Call<T> call, Throwable t);


}
