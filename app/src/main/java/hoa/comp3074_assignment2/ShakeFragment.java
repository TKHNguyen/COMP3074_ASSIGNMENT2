package hoa.comp3074_assignment2;
/*
* Course: COMP3074
* Name: Hoa Nguyen
* Student Number: 100959069
* */
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by hoa on 2016-11-05.
 */

public class ShakeFragment extends Fragment implements SensorEventListener {

    private TextView xText, yText,zText;
    private Sensor mySensor;
    private SensorManager sensorManager;
    private long currentTime, lastUpdate;
    private float x,y,z, last_x, last_y, last_z;
    private final static  long  UPDATEPERIOD = 300;
    private final static  long  SHAKE_THRESHOLD = 800;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_with_fragment,container,false);

        //Assign Text view
        xText = (TextView)view.findViewById(R.id.Xtxt);
        yText = (TextView)view.findViewById(R.id.Ytxt);
        zText = (TextView)view.findViewById(R.id.Ztxt);

        currentTime = lastUpdate = (long)0.0;
        x=y=z= last_x = last_y = last_z = (float)0.0;

        //Create sensor manager
        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);

        //Accelerometer sensor
        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null) {
            mySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            //register sensor listener
            sensorManager.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long currentTime = System.currentTimeMillis();
        if((currentTime-lastUpdate)>UPDATEPERIOD)
        {
            long different = (currentTime-lastUpdate);
            lastUpdate = currentTime;
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            float speed = Math.abs(x + y + z - last_x - last_y - last_z)/different*100000;

            //Update text view values
            xText.setText("X:  " + Math.abs(x));
            yText.setText("Y:  " + Math.abs(y));
            zText.setText("Z:  " + Math.abs(z));

            if(speed > SHAKE_THRESHOLD)
            {
                Toast.makeText(getActivity(),"A shake was detected \n speed " + speed, Toast.LENGTH_LONG).show();
                Intent j = new Intent(getActivity(), ShakeAlertActivity.class);
                startActivity(j);
                getActivity().finish();
            }
            last_x = x;
            last_y = y;
            last_z = z;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //register the accelerometer for listening the events
    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this,mySensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    //unregister the accelerometer for stop listening the events
    @Override
    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
