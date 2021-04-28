package shared;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        AppIsInForeGround.set(this.getClass(),true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppIsInForeGround.set(this.getClass(),false);
        if(AppIsInForeGround.isTrue()){
            //MessageBox.show("STILL IN FOREGROUND",this);
        }
        else{
            //MessageBox.show("SWITCHING TO BACKGROUND",this);

        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        //MessageBox.show("Attached to window",this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //MessageBox.show("DESTROYED",this);
    }
}