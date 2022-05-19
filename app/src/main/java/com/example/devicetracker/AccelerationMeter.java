package com.example.devicetracker;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

//Evet doğru kelimenin "accelerometer" olduğunu biliyorum.
public class AccelerationMeter extends Meter<Vector3>{

    private final SensorManager sensorManager;
    private final Sensor sensor;
    private final AccelerationSensorEventListener listener;

    private boolean calibrated = false;
    private Vector3 biasValue;

    public AccelerationMeter(SensorManager sensorManager){
        this.sensorManager = sensorManager;
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        listener = new AccelerationSensorEventListener();
    }

    @Override
    public void pause() {
        sensorManager.unregisterListener(listener);
    }

    @Override
    public void resume() {
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void calibrate() {
        calibrated = false;
    }

    private class AccelerationSensorEventListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            Vector3 vector = new Vector3(
                    sensorEvent.values[0],
                    sensorEvent.values[1],
                    sensorEvent.values[2]
            );
            if(calibrated) {
                setValue(Vector3.add(vector, biasValue), sensorEvent.timestamp);
            }
            else{
                biasValue = Vector3.scale(vector, -1);
                setValue(Vector3.ZERO, sensorEvent.timestamp);
                calibrated = true;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) { }
    }
}
