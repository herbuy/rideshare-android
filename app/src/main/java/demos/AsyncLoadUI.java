package demos;

import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import layers.render.Atom;
import libraries.android.MakeDummy;

public class AsyncLoadUI extends AsyncTask<Void,View,View> {
    AppCompatActivity context;
    LinearLayout viewHolder;

    public AsyncLoadUI(AppCompatActivity context) {
        this.context = context;
        execute();
    }


    @Override
    protected void onPreExecute() {
        viewHolder = MakeDummy.linearLayoutVertical(context);
        context.setContentView(MakeDummy.scrollView(viewHolder));
    }

    @Override
    protected View doInBackground(Void... voids) {

        for(int i = 0; i < 10000; i++){
            TextView item = Atom.textView(context,"Item "+i);
            publishProgress(item);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(View... values) {
        viewHolder.addView(values[0]);
    }

    @Override
    protected void onPostExecute(View view) {
        super.onPostExecute(view);
    }
}
