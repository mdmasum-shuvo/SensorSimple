package com.sensor.masum.sensor;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    TextView tv,tAcc,tGy,tLight,tMag,tPressure,tTemp,tHumi;
    private boolean color = false;
    long lastUpdate;
    private Sensor accelerometer,mGyro,mMango,mLight,mPressure,mTemp,mHumi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initVariable();

        tv.setBackgroundColor(Color.GREEN);
        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate=System.currentTimeMillis();



    }

    private void initVariable() {
        tv=findViewById(R.id.textView);
        tAcc=findViewById(R.id.textViewAcc);
        tMag=findViewById(R.id.textViewMango);
        tGy=findViewById(R.id.textViewGy);
        tLight=findViewById(R.id.textViewLight);
        tPressure=findViewById(R.id.textViewPressure);
        tTemp=findViewById(R.id.textViewMango);
        tHumi=findViewById(R.id.textViewHumidity);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            getAccelerometer(event);

        }

        else if (event.sensor.getType()==Sensor.TYPE_GYROSCOPE){
            getGyroscopSensor(event);
        }
        else if (event.sensor.getType()==Sensor.TYPE_LIGHT){
            getLightSensor(event);
        }
        else if (event.sensor.getType()==Sensor.TYPE_PRESSURE){
            getPressureSensor(event);
        }
        else if (event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
            getMagnetSensor(event);
        }
        else if (event.sensor.getType()==Sensor.TYPE_AMBIENT_TEMPERATURE){
            getTempSensor(event);
        }
        else if (event.sensor.getType()==Sensor.TYPE_RELATIVE_HUMIDITY){
            getHumiditycopSensor(event);
        }
        

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private void getAccelerometer(SensorEvent event) {

        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        tAcc.setText("Accelerometer Event value:\nX="+event.values[0]+"\tY="+event.values[1]+"\tZ="+event.values[2]);
        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = event.timestamp;
        if (accelationSquareRoot >= 2) //
        {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;
            Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT)
                    .show();
            if (color) {
                tv.setBackgroundColor(Color.GREEN);
            } else {
                tv.setBackgroundColor(Color.RED);
            }
            color = !color;
        }

    }


    private void getGyroscopSensor(SensorEvent event) {
        tGy.setText("Gyroscop Event value:\nX="+event.values[0]+"\tY="+event.values[1]+"\tZ="+event.values[2]);

    }

    private void getLightSensor(SensorEvent event) {
        tLight.setText("Light Event value:\tX="+event.values[0]);

    }

    private void getTempSensor(SensorEvent event) {
        tTemp.setText("Temp Event value:\tX="+event.values[0]);
    }

    private void getPressureSensor(SensorEvent event) {
        tPressure.setText("Pressure Event value:\tX="+event.values[0]);
    }

    private void getMagnetSensor(SensorEvent event) {
        tMag.setText("Magnet Event value:\nX="+event.values[0]+"\tY="+event.values[1]+"\tZ="+event.values[2]);
    }
    private void getHumiditycopSensor(SensorEvent event) {
        tHumi.setText("Humidity Event value:\nX="+event.values[0]);
    }






    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        registerAllSensor();

    }

    private void registerAllSensor(){
        accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyro=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mMango=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mLight=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mHumi=sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        mPressure=sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mTemp=sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (accelerometer!=null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            tAcc.setText("accelerometer sensor not found");
        }

        if (mGyro!=null){
            sensorManager.registerListener(this,mGyro,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            tGy.setText("Gyroscope sensor not found");
        }
        if (mMango!=null){
            sensorManager.registerListener(this,mMango,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            tMag.setText("Magnetic sensor not found");
        }

        if (mLight!=null){
            sensorManager.registerListener(this,mLight,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            tLight.setText("Lighty sensor not found");
        }

        if (mHumi!=null){
            sensorManager.registerListener(this,mHumi,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            tHumi.setText("Humidity sensor not found");
        }

        if (mPressure!=null){
            sensorManager.registerListener(this,mPressure,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            tPressure.setText("Pressure sensor not found");
        }


        if (mTemp!=null){
            sensorManager.registerListener(this,mPressure,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            tTemp.setText("Temparature sensor not found");
        }
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
