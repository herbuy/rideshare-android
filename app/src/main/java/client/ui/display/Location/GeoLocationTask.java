package client.ui.display.Location;

import android.content.Context;
import android.os.AsyncTask;

import libraries.android.AddressListFromString;

public abstract class GeoLocationTask {

    public GeoLocationTask() {

    }

    protected abstract Context getContext();

    public void search(String searchQuery){
        TasK tasK = new TasK(getContext());
        tasK.setBeforeExecute(new TasK.BeforeExecute(){
            @Override
            public void run(){
                GeoLocationTask.this.beforeGeolocate();
            }
        });
        tasK.setAfterExecute(new TasK.AfterExecute(){
            @Override
            public void run(AddressListFromString addressListFromString) {
                GeoLocationTask.this.AfterGeolocate(addressListFromString);
            }
        });
        tasK.execute(searchQuery);
    }

    protected abstract void beforeGeolocate();
    protected abstract void AfterGeolocate(AddressListFromString addressListFromString);

    private static class TasK extends AsyncTask<String, Void, AddressListFromString>{
        Context context;
        private BeforeExecute beforeExecute;
        private AfterExecute afterExecute;

        public TasK(Context context) {
            this.context = context;
        }

        @Override
        protected AddressListFromString doInBackground(String... params) {
            return new AddressListFromString(context, params[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(beforeExecute != null){
                beforeExecute.run();
            }

        }

        @Override
        protected void onPostExecute(AddressListFromString addressListFromString) {
            super.onPostExecute(addressListFromString);
            if(afterExecute != null){
                afterExecute.run(addressListFromString);
            }
        }

        public void setBeforeExecute(BeforeExecute beforeExecute) {
            this.beforeExecute = beforeExecute;
        }

        public void setAfterExecute(AfterExecute afterExecute) {
            this.afterExecute = afterExecute;
        }

        public AfterExecute getAfterExecute() {
            return afterExecute;
        }

        private interface BeforeExecute {
            public void run();
        }

        private interface AfterExecute {
            void run(AddressListFromString addressListFromString);
        }

        //protected abstract void afterExecute(AddressListFromString addressListFromString);
        //protected abstract void beforeExecute();
    }



}
