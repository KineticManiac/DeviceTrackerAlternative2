package com.example.devicetracker;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class RotationMeter extends Meter<Rotation>{

    private final SensorManager sensorManager;
    private final OnRotationSensorEventListener listener;
    private final Sensor sensor;

    public RotationMeter(SensorManager sensorManager){
        this.sensorManager = sensorManager;
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        listener = new OnRotationSensorEventListener();
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
        //Do nothing
    }

    private class OnRotationSensorEventListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            setValue(new Rotation(
                    new Angle(sensorEvent.values[0]),
                    new Angle(sensorEvent.values[1]),
                    new Angle(sensorEvent.values[2])
            ), sensorEvent.timestamp);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) { }
    }
}
