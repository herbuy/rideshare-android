package libraries.android;

import android.os.AsyncTask;

public class HerbuyAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {


    private Runnable beforeSearch;
    public HerbuyAsyncTask<Params, Progress, Result> setBeforeSearch(Runnable beforeSearch) {
        this.beforeSearch = beforeSearch;
        return this;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(beforeSearch != null){
            beforeSearch.run();
        }
    }

    public interface DoInBackground<String,AddressListFromString>{
        AddressListFromString run(String... input);
    }
    private DoInBackground<Params, Result> doInBackground;

    public HerbuyAsyncTask<Params, Progress, Result> setDoInBackground(DoInBackground<Params, Result> doInBackground) {
        this.doInBackground = doInBackground;
        return this;
    }

    @Override
    protected Result doInBackground(Params... params) {
        return doInBackground.run(params);
    }

    public interface OnComplete<AddressListFromString>{
        void run(AddressListFromString addressListFromString);
    }

    private OnComplete<Result> onComplete;
    public HerbuyAsyncTask<Params, Progress, Result> setOnComplete(OnComplete<Result> onComplete) {
        this.onComplete = onComplete;
        return this;
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if(onComplete != null){
            onComplete.run(result);
        }
    }
}
