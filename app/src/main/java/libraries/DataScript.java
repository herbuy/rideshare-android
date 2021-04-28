package libraries;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class DataScript<DataType> {
    private DataFetchScript<DataType> dataFetchScript;
    private DataDisplayScript<DataType> dataDisplayScript;

    private  DataType lastData;
    private  String lastError = "";

    protected final Context context;
    private RelativeLayout relativeLayout;

    public DataScript(Context context) {
        this.context = context;
        relativeLayout = new RelativeLayout(context);

    }

    public View run() {
        dataFetchScript.fetchData(new DataFetchResult<DataType>() {
            @Override
            public void success(DataType data) {
                lastData = data;
                lastError = "";
                dataDisplayScript.success(data);
                changeViewTo(dataDisplayScript.getView());
            }

            @Override
            public void error(String message) {
                lastError = message;
                dataDisplayScript.error(message);
                changeViewTo(dataDisplayScript.getView());
            }
        });

        return dataDisplayScript.getView();
    }

    private void changeViewTo(View content) {
        relativeLayout.removeAllViews();
        relativeLayout.addView(content);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        content.setLayoutParams(layoutParams);

    }

    public void runDisplayScript(){
        if(lastError.trim().equalsIgnoreCase("")){
            dataDisplayScript.success(lastData);
            changeViewTo(dataDisplayScript.getView());
        }
        else{
            dataDisplayScript.error(lastError);
            changeViewTo(dataDisplayScript.getView());
        }

    }


    public View getView(){
        return relativeLayout;
    }

    public interface DataDisplayScript<DataType>{
        void success(DataType data);
        void error(String message);
        View getView();
    }


    public interface DataFetchResult<DataType>{
        void success(DataType data);
        void error(String error);

    }

    public static abstract class DataFetchScript<DataType> {
        public abstract void fetchData(DataFetchResult<DataType> result);
    }

    public void setDataFetchScript(DataFetchScript<DataType> dataFetchScript){
        this.dataFetchScript = dataFetchScript;
    }

    public void setDataDisplayScript(DataDisplayScript<DataType> dataDisplayScript) {
        this.dataDisplayScript = dataDisplayScript;
    }
}
