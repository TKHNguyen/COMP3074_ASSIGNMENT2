package hoa.comp3074_assignment2;
/*
* Course: COMP3074
* Name: Hoa Nguyen
* Student Number: 100959069
* */
import android.app.Activity;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
/**
 * Created by hoa on 2016-11-05.
 */

public class ShakeAlertActivity extends Activity implements OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_alert);
        TextView message =(TextView)findViewById(R.id.textView);
        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(this);
    }

    //back to main activity
    @Override
    public void onClick(View v){
        Intent j = new Intent(this, MainActivity.class);
        startActivity(j);

    }

    //back to main activity
    @Override
    public void onBackPressed() {

        finish();
        startActivity(new Intent(ShakeAlertActivity.this, MainActivity.class));
    }
}
