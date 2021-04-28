package libraries.android;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.util.List;

import client.data.AppCallback;
import libraries.TimeMonitor;
import retrofit2.Call;
import retrofit2.Response;


//updating a list is a single responsibility
public abstract class ListViewUpdater<ListItemType> {
    private boolean isBusy = false;
    protected final Context context;

    public ListViewUpdater(Context context) {
        this.context = context;
        if(autoStart()){
            start();
        }

    }
    protected boolean autoStart(){
        return true;
    }

    protected abstract void init();

    //List<ListItemType> cachedData = new ArrayList<>();

    protected void start() {
        init();
        readAndDisplayCachedData();
        tryUpdateCache();
        addRefreshTriggers();
    }

    private void addRefreshTriggers() {

        listSpecificTriggers();
    }

    private void listSpecificTriggers() {
        listenForRefreshTriggers(new Runnable() {
            @Override
            public void run() {
                tryUpdateCache();
            }
        });

    }

    private void readAndDisplayCachedData() {

        List<ListItemType> cachedData = getCachedData();


        if(cachedData.size() > 0){
            TimeMonitor.start("updating_view");
            updateView(cachedData);
            TimeMonitor.stop("updating_view");
        }
        else{
            renderEmpty();
        }

    }

    synchronized protected void tryUpdateCache() {

        log("Checking if can start updating cache");
        if(isBusy){
            log("Update already in progress");
            return;
        }
        isBusy = true;
        log("Update started");



        //if not null or empty, render

        //no matter whether empty or available, download the fresh data to update the chache
        //first download the data and update
        if(cacheEmpty()){
            renderLoading();
        }

        getCall().enqueue(new AppCallback<List<ListItemType>>() {
            @Override
            protected void onSuccess(Call<List<ListItemType>> call, Response<List<ListItemType>> response) {
                isBusy = false;
                updateCache(response.body());
                readAndDisplayCachedData();
                log("Update successful");
            }

            @Override
            protected void onError(Call<List<ListItemType>> call, Throwable t) {
                isBusy = false;
                log("Update failed");
                if (cacheEmpty()) {
                    renderError(t.getMessage(), new Runnable() {
                        @Override
                        public void run() {
                            tryUpdateCache();
                        }
                    });
                }

            }
        });


    }

    protected void log(String message) {
        Log.d(getClass().getSimpleName(), message);
    }


    private boolean cacheEmpty() {
        return getCachedData().size() < 1;
    }

    private void updateCache(List<ListItemType> newData) {
        clearCache();
        if(newData != null){
            for(ListItemType item : newData){
                addToCache(item);
            }
        }
    }

    private String getClassName() {
        return getClass().getSimpleName();
    }



    //-------------- abstract methods -----------------------
    protected abstract List<ListItemType> getCachedData();

    protected abstract void clearCache();

    protected abstract void addToCache(ListItemType item);

    protected abstract void updateView(List<ListItemType> data);

    protected abstract void renderEmpty();

    protected abstract void renderLoading();

    protected abstract void renderError(String message, Runnable runnable);

    protected abstract Call<List<ListItemType>> getCall();


    protected abstract void listenForRefreshTriggers(Runnable runnable);



    public abstract View getView();
}
