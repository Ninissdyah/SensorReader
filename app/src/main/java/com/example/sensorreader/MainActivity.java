package com.example.sensorreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;

    //untuk mengambil atribut sensor
    private Sensor mSensorProximity;
    private Sensor mSensorLight;
    private Sensor mSensorPressure;
    private Sensor mSensorAmbient;
    private Sensor mSensorHumidity;

    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;
    private TextView mTextSensorPressure;
    private TextView mTextSensorAmbient;
    private TextView mTextSensorHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //iniz
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //variabel list untuk nampung sensor nya apa aja, tipenya objek
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        //menyimpan data sensor
        StringBuilder sensorText = new StringBuilder();

        //looping data sensor
        for (Sensor currentSensor : sensorList) {
            sensorText.append(currentSensor.getName()).append(System.getProperty("line.separator"));
        }

        //ambil di xml
        TextView sensorTextView = findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);

        //inis
        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);
        mTextSensorAmbient = findViewById(R.id.label_ambient);
        mTextSensorHumidity = findViewById(R.id.label_humidity);
        mTextSensorPressure = findViewById(R.id.label_pressure);

        //ambil dari sensor manager
        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorAmbient = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mSensorPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);




        //cek sensor ada ato tak
        String sensor_error = "No sensor";
        if (mSensorLight == null) {
            mTextSensorLight.setText(sensor_error);
        }
        if (mSensorProximity == null) {
            mTextSensorProximity.setText(sensor_error);
        }
        if (mSensorPressure == null) {
            mTextSensorPressure.setText(sensor_error);
        }
        if (mSensorAmbient == null) {
            mTextSensorAmbient.setText(sensor_error);
        }
        if (mSensorProximity == null) {
            mTextSensorProximity.setText(sensor_error);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mSensorProximity != null) {
            mSensorManager.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(mSensorPressure != null) {
            mSensorManager.registerListener(this, mSensorPressure, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorAmbient != null) {
            mSensorManager.registerListener(this, mSensorAmbient, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorHumidity != null) {
            mSensorManager.registerListener(this, mSensorHumidity, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //mendapatkan sensor type
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];
        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(String.format("Light sensor : %1$.2f", currentValue));
                changeBackgroundColor(currentValue);
                break;
            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(String.format("Proximity sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mTextSensorAmbient.setText(String.format("Ambient sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_PRESSURE:
                mTextSensorPressure.setText(String.format("Pressure sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                mTextSensorHumidity.setText(String.format("Humidity sensor : %1$.2f", currentValue));
                break;
            default:
        }

    }

    private void changeBackgroundColor(float currentValue) {
        ConstraintLayout layout = findViewById(R.id.layout_constraint);
        if(currentValue >= 20000 && currentValue <= 40000){
            layout.setBackgroundColor(Color.RED);
        } else if(currentValue >= 0 && currentValue < 20000) {
            layout.setBackgroundColor(Color.BLUE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}